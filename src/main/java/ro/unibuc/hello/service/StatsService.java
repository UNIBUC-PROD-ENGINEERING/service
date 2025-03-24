package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.BidEntity;
import ro.unibuc.hello.data.BidRepository;
import ro.unibuc.hello.data.Category;
import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.ItemRepository;
import ro.unibuc.hello.dto.AuctionStats;
import ro.unibuc.hello.dto.ItemPopularity;
import ro.unibuc.hello.dto.UserStats;
import ro.unibuc.hello.exception.EntityNotFoundException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class StatsService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private ItemService itemService;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
    );

    // Threshold for "hot" items (bids per day)
    private static final double HOT_ITEM_THRESHOLD = 5.0;

    // Weight factors for popularity score
    private static final double WEIGHT_TOTAL_BIDS = 0.3;
    private static final double WEIGHT_UNIQUE_BIDDERS = 0.25;
    private static final double WEIGHT_BID_FREQUENCY = 0.2;
    private static final double WEIGHT_PRICE_INCREASE = 0.15;
    private static final double WEIGHT_RECENCY = 0.1;

    /**
     * Get overall platform statistics
     */
    public AuctionStats getOverallStats() {
        AuctionStats stats = new AuctionStats();

        // Get all items and bids
        List<ItemEntity> allItems = itemRepository.findAll();
        List<BidEntity> allBids = bidRepository.findAll();

        // Calculate basic stats
        stats.setTotalItems(allItems.size());
        stats.setActiveItems((int) allItems.stream().filter(ItemEntity::isActive).count());
        stats.setCompletedAuctions((int) allItems.stream().filter(item -> !item.isActive()).count());
        stats.setTotalBids(allBids.size());

        // Calculate average bids per item
        stats.setAverageBids(allItems.isEmpty() ? 0 : (double) allBids.size() / allItems.size());
        stats.setBidsPerItem(stats.getAverageBids());

        // Find highest bid amount
        double highestBid = allBids.stream()
                .mapToDouble(BidEntity::getAmount)
                .max()
                .orElse(0.0);
        stats.setHighestBidAmount(highestBid);

        // Calculate average bid amount
        double avgBidAmount = allBids.stream()
                .mapToDouble(BidEntity::getAmount)
                .average()
                .orElse(0.0);
        stats.setAverageBidAmount(avgBidAmount);

        // Count items by category
        Map<String, Integer> itemsByCategory = new HashMap<>();
        for (Category category : Category.values()) {
            int count = (int) allItems.stream()
                    .filter(item -> item.getCategory() == category)
                    .count();
            itemsByCategory.put(category.name(), count);
        }
        stats.setItemsByCategory(itemsByCategory);

        // Calculate unique bidders
        Set<String> uniqueBidderEmails = allBids.stream()
                .map(BidEntity::getEmail)
                .collect(Collectors.toSet());
        stats.setUniqueBidders(uniqueBidderEmails.size());

        // Calculate bids per day
        if (!allBids.isEmpty()) {
            // Get earliest and latest bid dates
            LocalDateTime earliestBid = allBids.stream()
                    .map(BidEntity::getCreatedAt)
                    .min(LocalDateTime::compareTo)
                    .orElse(LocalDateTime.now());

            LocalDateTime latestBid = allBids.stream()
                    .map(BidEntity::getCreatedAt)
                    .max(LocalDateTime::compareTo)
                    .orElse(LocalDateTime.now());

            // Calculate days between first and last bid
            long daysBetween = Duration.between(earliestBid, latestBid).toDays() + 1; // add 1 to include the first day
            stats.setBidsPerDay(daysBetween > 0 ? (double) allBids.size() / daysBetween : allBids.size());
        } else {
            stats.setBidsPerDay(0);
        }

        // Calculate bids by day of week
        Map<String, Integer> bidsByDay = new HashMap<>();
        for (BidEntity bid : allBids) {
            String dayOfWeek = bid.getCreatedAt().getDayOfWeek().toString();
            bidsByDay.put(dayOfWeek, bidsByDay.getOrDefault(dayOfWeek, 0) + 1);
        }
        stats.setBidsByDay(bidsByDay);

        // Calculate category popularity (bids per item)
        Map<String, Double> categoryPopularity = new HashMap<>();
        for (Category category : Category.values()) {
            int categoryItems = itemsByCategory.getOrDefault(category.name(), 0);
            if (categoryItems > 0) {
                int categoryBids = 0;
                for (ItemEntity item : allItems) {
                    if (item.getCategory() == category) {
                        categoryBids += bidRepository.findByItemId(item.getId()).size();
                    }
                }
                double popularityRatio = (double) categoryBids / categoryItems;
                categoryPopularity.put(category.name(), popularityRatio);
            } else {
                categoryPopularity.put(category.name(), 0.0);
            }
        }
        stats.setCategoryPopularity(categoryPopularity);

        // Calculate top bidders
        Map<String, Integer> bidderCounts = new HashMap<>();
        for (BidEntity bid : allBids) {
            bidderCounts.put(bid.getEmail(), bidderCounts.getOrDefault(bid.getEmail(), 0) + 1);
        }

        // Sort and limit to top 5 bidders
        Map<String, Integer> topBidders = bidderCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        stats.setTopBidders(topBidders);

        // Calculate most active hours
        Map<String, Integer> hourDistribution = new HashMap<>();
        for (BidEntity bid : allBids) {
            String hour = String.valueOf(bid.getCreatedAt().getHour());
            hourDistribution.put(hour, hourDistribution.getOrDefault(hour, 0) + 1);
        }
        stats.setMostActiveTimes(hourDistribution);

        // Calculate overall popularity score (normalized scale of 0-100)
        // Based on unique bidders, bids per item, and bid frequency
        double bidderScore = Math.min(1.0, stats.getUniqueBidders() / 100.0) * 100; // Cap at 100 unique bidders
        double bidsPerItemScore = Math.min(1.0, stats.getBidsPerItem() / 20.0) * 100; // Cap at 20 bids per item
        double frequencyScore = Math.min(1.0, stats.getBidsPerDay() / 50.0) * 100; // Cap at 50 bids per day

        double popularityScore = (bidderScore * 0.4) + (bidsPerItemScore * 0.4) + (frequencyScore * 0.2);
        stats.setPopularityScore(popularityScore);

        return stats;
    }

    /**
     * Get user-specific statistics
     */
    public UserStats getUserStats(String email) {
        // Validate email
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }

        UserStats stats = new UserStats();
        stats.setEmail(email);

        // Get user's items and bids
        List<ItemEntity> userItems = itemRepository.findAll().stream()
                .filter(item -> item.getCreator().equals(email))
                .collect(Collectors.toList());

        List<BidEntity> userBids = bidRepository.findByEmail(email);

        // Calculate user's item stats
        stats.setTotalItemsListed(userItems.size());
        stats.setActiveItemsListed((int) userItems.stream().filter(ItemEntity::isActive).count());
        stats.setCompletedAuctions((int) userItems.stream().filter(item -> !item.isActive()).count());

        // Calculate user's bid stats
        stats.setTotalBidsMade(userBids.size());

        // Count bids made in the last week
        LocalDateTime oneWeekAgo = LocalDateTime.now().minusDays(7);
        int recentBids = (int) userBids.stream()
                .filter(bid -> bid.getCreatedAt().isAfter(oneWeekAgo))
                .count();
        stats.setBidsMadeLastWeek(recentBids);

        // Count bids won (highest bid on completed auctions)
        int bidsWon = 0;
        double totalSpent = 0.0;

        List<ItemEntity> allItems = itemRepository.findAll();
        for (ItemEntity item : allItems) {
            if (!item.isActive()) {
                List<BidEntity> itemBids = bidRepository.findByItemIdOrderByAmountDesc(item.getId());
                if (!itemBids.isEmpty()) {
                    BidEntity highestBid = itemBids.get(0);
                    if (highestBid.getEmail().equals(email)) {
                        bidsWon++;
                        totalSpent += highestBid.getAmount();
                    }
                }
            }
        }
        stats.setBidsWon(bidsWon);
        stats.setTotalSpent(totalSpent);

        // Calculate user's earnings (sum of highest bids on user's completed auctions)
        double totalEarned = 0.0;
        for (ItemEntity item : userItems) {
            if (!item.isActive()) {
                List<BidEntity> itemBids = bidRepository.findByItemIdOrderByAmountDesc(item.getId());
                if (!itemBids.isEmpty()) {
                    totalEarned += itemBids.get(0).getAmount();
                }
            }
        }
        stats.setTotalEarned(totalEarned);

        // Calculate user's bidding frequency (bids per day)
        if (!userBids.isEmpty()) {
            LocalDateTime firstBid = userBids.stream()
                    .map(BidEntity::getCreatedAt)
                    .min(LocalDateTime::compareTo)
                    .orElse(LocalDateTime.now());

            LocalDateTime lastBid = userBids.stream()
                    .map(BidEntity::getCreatedAt)
                    .max(LocalDateTime::compareTo)
                    .orElse(LocalDateTime.now());

            long daysBetween = Duration.between(firstBid, lastBid).toDays() + 1;
            stats.setBiddingFrequency(daysBetween > 0 ? (double) userBids.size() / daysBetween : userBids.size());
        } else {
            stats.setBiddingFrequency(0);
        }

        // Count unique items bid on
        Set<String> uniqueItemsBidOn = userBids.stream()
                .map(BidEntity::getItemId)
                .collect(Collectors.toSet());
        stats.setUniqueItemsBidOn(uniqueItemsBidOn.size());

        // Calculate average bid amount
        double avgBidAmount = userBids.stream()
                .mapToDouble(BidEntity::getAmount)
                .average()
                .orElse(0);
        stats.setAverageBidAmount(avgBidAmount);

        // Calculate bids by category
        Map<String, Integer> bidsByCategory = new HashMap<>();
        for (Category category : Category.values()) {
            bidsByCategory.put(category.name(), 0);
        }

        for (BidEntity bid : userBids) {
            Optional<ItemEntity> itemOpt = itemRepository.findById(bid.getItemId());
            if (itemOpt.isPresent()) {
                String categoryName = itemOpt.get().getCategory().name();
                bidsByCategory.put(categoryName, bidsByCategory.getOrDefault(categoryName, 0) + 1);
            }
        }
        stats.setBidsByCategory(bidsByCategory);

        // Calculate bidding time distribution
        Map<String, Integer> hourDistribution = new HashMap<>();
        for (BidEntity bid : userBids) {
            String hour = String.valueOf(bid.getCreatedAt().getHour());
            hourDistribution.put(hour, hourDistribution.getOrDefault(hour, 0) + 1);
        }
        stats.setBiddingTimeDistribution(hourDistribution);

        // Calculate bid streak (consecutive days)
        // First, group bids by day
        Map<LocalDate, List<BidEntity>> bidsByDate = userBids.stream()
                .collect(Collectors.groupingBy(bid -> bid.getCreatedAt().toLocalDate()));

        if (!bidsByDate.isEmpty()) {
            List<LocalDate> bidDates = new ArrayList<>(bidsByDate.keySet());
            Collections.sort(bidDates);

            int maxStreak = 1;
            int currentStreak = 1;

            for (int i = 1; i < bidDates.size(); i++) {
                LocalDate prevDate = bidDates.get(i-1);
                LocalDate currDate = bidDates.get(i);

                if (prevDate.plusDays(1).equals(currDate)) {
                    currentStreak++;
                } else {
                    maxStreak = Math.max(maxStreak, currentStreak);
                    currentStreak = 1;
                }
            }

            maxStreak = Math.max(maxStreak, currentStreak);
            stats.setBidStreak(maxStreak);
        } else {
            stats.setBidStreak(0);
        }

        // Calculate user activity score (0-100)
        double frequencyScore = Math.min(1.0, stats.getBiddingFrequency() / 5.0) * 100; // Cap at 5 bids per day
        double recentActivityScore = Math.min(1.0, (double) recentBids / 20.0) * 100; // Cap at 20 bids in last week
        double diversityScore = Math.min(1.0, (double) stats.getUniqueItemsBidOn() / 20.0) * 100; // Cap at 20 unique items
        double winRateScore = userBids.isEmpty() ? 0 : Math.min(1.0, (double) bidsWon / userBids.size()) * 100;
        double streakScore = Math.min(1.0, (double) stats.getBidStreak() / 7.0) * 100; // Cap at 7-day streak

        double activityScore = (frequencyScore * 0.25) + (recentActivityScore * 0.25) +
                (diversityScore * 0.2) + (winRateScore * 0.15) + (streakScore * 0.15);

        stats.setUserActivityScore(activityScore);

        return stats;
    }

    /**
     * Get item popularity statistics
     */
    public ItemPopularity getItemPopularity(String itemId) {
        // Check if item exists
        ItemEntity item = itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException(itemId));

        List<BidEntity> itemBids = bidRepository.findByItemId(itemId);
        Collections.sort(itemBids, Comparator.comparing(BidEntity::getCreatedAt));

        ItemPopularity popularity = new ItemPopularity();
        popularity.setItemId(itemId);
        popularity.setItemName(item.getName());
        popularity.setTotalBids(itemBids.size());

        // Count unique bidders
        Set<String> uniqueBidders = itemBids.stream()
                .map(BidEntity::getEmail)
                .collect(Collectors.toSet());
        popularity.setUniqueBidders(uniqueBidders.size());

        // Calculate bid frequency (bids per day)
        if (!itemBids.isEmpty()) {
            LocalDateTime firstBid = itemBids.get(0).getCreatedAt();
            LocalDateTime lastBid = itemBids.get(itemBids.size() - 1).getCreatedAt();
            popularity.setLastBidTime(lastBid);

            long daysBetween = Duration.between(firstBid, lastBid).toDays() + 1;
            double bidFrequency = daysBetween > 0 ? (double) itemBids.size() / daysBetween : itemBids.size();
            popularity.setBidFrequency(bidFrequency);

            // Mark as "hot" if bid frequency exceeds threshold
            popularity.setHot(bidFrequency >= HOT_ITEM_THRESHOLD);

            // Calculate bid increase rate
            if (itemBids.size() >= 2) {
                double totalIncreasePercent = 0;
                double prevAmount = itemBids.get(0).getAmount();
                int increaseCount = 0;

                for (int i = 1; i < itemBids.size(); i++) {
                    double currentAmount = itemBids.get(i).getAmount();
                    if (currentAmount > prevAmount) {
                        double increasePercent = (currentAmount - prevAmount) / prevAmount * 100;
                        totalIncreasePercent += increasePercent;
                        increaseCount++;
                    }
                    prevAmount = currentAmount;
                }

                double avgIncreaseRate = increaseCount > 0 ? totalIncreasePercent / increaseCount : 0;
                popularity.setBidIncreaseRate(avgIncreaseRate);
            } else {
                popularity.setBidIncreaseRate(0);
            }
        } else {
            popularity.setBidFrequency(0);
            popularity.setBidIncreaseRate(0);
            popularity.setHot(false);
        }

        // Calculate price increase from initial price
        if (!itemBids.isEmpty()) {
            double initialPrice = item.getInitialPrice();
            double currentHighestBid = itemBids.stream()
                    .mapToDouble(BidEntity::getAmount)
                    .max()
                    .orElse(initialPrice);

            double priceIncrease = initialPrice > 0 ?
                    (currentHighestBid - initialPrice) / initialPrice * 100 : 0;
            popularity.setPriceIncrease(priceIncrease);
        } else {
            popularity.setPriceIncrease(0);
        }

        // Calculate popularity score (0-100)
        calculateItemPopularityScore(popularity, item);

        return popularity;
    }

    /**
     * Calculate a popularity score for an item
     */
    private void calculateItemPopularityScore(ItemPopularity popularity, ItemEntity item) {
        // Normalize factors to a 0-100 scale
        double bidScore = Math.min(1.0, (double) popularity.getTotalBids() / 50.0) * 100; // Cap at 50 bids
        double bidderScore = Math.min(1.0, (double) popularity.getUniqueBidders() / 20.0) * 100; // Cap at 20 unique bidders
        double frequencyScore = Math.min(1.0, popularity.getBidFrequency() / 10.0) * 100; // Cap at 10 bids per day
        double priceIncreaseScore = Math.min(1.0, popularity.getPriceIncrease() / 200.0) * 100; // Cap at 200% increase

        // Recency factor (higher score for more recent activity)
        double recencyScore = 0;
        if (popularity.getLastBidTime() != null) {
            long hoursSinceLastBid = Duration.between(popularity.getLastBidTime(), LocalDateTime.now()).toHours();
            recencyScore = Math.max(0, 100 - (hoursSinceLastBid / 24.0) * 100); // Full score if within last day, decreases over time
            recencyScore = Math.max(0, recencyScore); // Ensure non-negative
        }

        // Weight the factors for final score
        double score = (bidScore * WEIGHT_TOTAL_BIDS) +
                (bidderScore * WEIGHT_UNIQUE_BIDDERS) +
                (frequencyScore * WEIGHT_BID_FREQUENCY) +
                (priceIncreaseScore * WEIGHT_PRICE_INCREASE) +
                (recencyScore * WEIGHT_RECENCY);

        popularity.setPopularityScore(score);
    }

    /**
     * Get category statistics
     */
    public AuctionStats getCategoryStats(String categoryStr) {
        // Validate category
        Category category;
        try {
            category = Category.valueOf(categoryStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid category: " + categoryStr);
        }

        AuctionStats stats = new AuctionStats();

        // Get all items in this category
        List<ItemEntity> categoryItems = itemRepository.findAll().stream()
                .filter(item -> item.getCategory() == category)
                .collect(Collectors.toList());

        // Calculate basic stats
        stats.setTotalItems(categoryItems.size());
        stats.setActiveItems((int) categoryItems.stream().filter(ItemEntity::isActive).count());
        stats.setCompletedAuctions((int) categoryItems.stream().filter(item -> !item.isActive()).count());

        // Calculate bid statistics
        int totalBids = 0;
        Set<String> uniqueBidderEmails = new HashSet<>();
        double totalBidAmount = 0.0;
        double highestBid = 0.0;
        LocalDateTime earliestBid = null;
        LocalDateTime latestBid = null;
        Map<String, Integer> bidsByDayOfWeek = new HashMap<>();

        for (ItemEntity item : categoryItems) {
            List<BidEntity> itemBids = bidRepository.findByItemId(item.getId());
            totalBids += itemBids.size();

            for (BidEntity bid : itemBids) {
                // Track unique bidders
                uniqueBidderEmails.add(bid.getEmail());

                // Track bid amounts
                totalBidAmount += bid.getAmount();
                if (bid.getAmount() > highestBid) {
                    highestBid = bid.getAmount();
                }

                // Track bid times
                if (earliestBid == null || bid.getCreatedAt().isBefore(earliestBid)) {
                    earliestBid = bid.getCreatedAt();
                }
                if (latestBid == null || bid.getCreatedAt().isAfter(latestBid)) {
                    latestBid = bid.getCreatedAt();
                }

                // Track day of week
                String dayOfWeek = bid.getCreatedAt().getDayOfWeek().toString();
                bidsByDayOfWeek.put(dayOfWeek, bidsByDayOfWeek.getOrDefault(dayOfWeek, 0) + 1);
            }
        }

        stats.setTotalBids(totalBids);
        stats.setUniqueBidders(uniqueBidderEmails.size());
        stats.setAverageBids(categoryItems.isEmpty() ? 0 : (double) totalBids / categoryItems.size());
        stats.setBidsPerItem(stats.getAverageBids());
        stats.setHighestBidAmount(highestBid);
        stats.setAverageBidAmount(totalBids == 0 ? 0 : totalBidAmount / totalBids);

        // Calculate bids per day
        if (earliestBid != null && latestBid != null) {
            long daysBetween = Duration.between(earliestBid, latestBid).toDays() + 1;
            stats.setBidsPerDay(daysBetween > 0 ? (double) totalBids / daysBetween : totalBids);
        } else {
            stats.setBidsPerDay(0);
        }

        stats.setBidsByDay(bidsByDayOfWeek);

        // Set category counter
        Map<String, Integer> categoryCount = new HashMap<>();
        categoryCount.put(category.name(), categoryItems.size());
        stats.setItemsByCategory(categoryCount);

        // Calculate popularity score
        double uniqueBidderFactor = Math.min(1.0, (double) stats.getUniqueBidders() / 50.0);
        double bidsPerItemFactor = Math.min(1.0, stats.getBidsPerItem() / 10.0);
        double bidsPerDayFactor = Math.min(1.0, stats.getBidsPerDay() / 20.0);

        double popularityScore = (uniqueBidderFactor * 0.4 + bidsPerItemFactor * 0.4 + bidsPerDayFactor * 0.2) * 100;
        stats.setPopularityScore(popularityScore);

        return stats;
    }

    /**
     * Get most popular items based on popularity score
     */
    public List<ItemPopularity> getPopularItems(int limit) {
        List<ItemEntity> activeItems = itemRepository.findByActive(true);
        List<ItemPopularity> popularityList = new ArrayList<>();

        for (ItemEntity item : activeItems) {
            ItemPopularity popularity = getItemPopularity(item.getId());
            popularityList.add(popularity);
        }

        // Sort by popularity score in descending order and limit
        return popularityList.stream()
                .sorted(Comparator.comparing(ItemPopularity::getPopularityScore).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Get most popular categories based on category popularity
     */
    public Map<String, Double> getPopularCategories() {
        AuctionStats stats = getOverallStats();
        Map<String, Double> categoryPopularity = stats.getCategoryPopularity();

        // Sort by popularity in descending order
        return categoryPopularity.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    /**
     * Get most active users based on activity score
     */
    public List<UserStats> getMostActiveUsers(int limit) {
        List<BidEntity> allBids = bidRepository.findAll();

        // Get unique bidder emails
        Set<String> bidderEmails = allBids.stream()
                .map(BidEntity::getEmail)
                .collect(Collectors.toSet());

        // Calculate user stats for each bidder
        List<UserStats> allUserStats = new ArrayList<>();
        for (String email : bidderEmails) {
            try {
                UserStats userStats = getUserStats(email);
                allUserStats.add(userStats);
            } catch (Exception e) {
                // Skip users with invalid stats
            }
        }

        // Sort by activity score in descending order and limit
        return allUserStats.stream()
                .sorted(Comparator.comparing(UserStats::getUserActivityScore).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Get hourly distribution of bidding activity
     */
    public Map<String, Integer> getBiddingHourDistribution() {
        List<BidEntity> allBids = bidRepository.findAll();
        Map<String, Integer> hourDistribution = new HashMap<>();

        // Initialize all hours with zero
        for (int i = 0; i < 24; i++) {
            hourDistribution.put(String.valueOf(i), 0);
        }

        // Count bids per hour
        for (BidEntity bid : allBids) {
            String hour = String.valueOf(bid.getCreatedAt().getHour());
            hourDistribution.put(hour, hourDistribution.getOrDefault(hour, 0) + 1);
        }

        return hourDistribution;
    }

    /**
     * Get items with high bidding activity ("hot" items)
     */
    public List<ItemPopularity> getHotItems() {
        List<ItemEntity> activeItems = itemRepository.findByActive(true);
        List<ItemPopularity> hotItems = new ArrayList<>();

        for (ItemEntity item : activeItems) {
            ItemPopularity popularity = getItemPopularity(item.getId());
            if (popularity.isHot()) {
                hotItems.add(popularity);
            }
        }

        // Sort by bid frequency in descending order
        return hotItems.stream()
                .sorted(Comparator.comparing(ItemPopularity::getBidFrequency).reversed())
                .collect(Collectors.toList());
    }
}