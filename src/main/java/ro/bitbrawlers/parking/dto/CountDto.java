package ro.bitbrawlers.parking.dto;

public class CountDto {
    private Integer emptySpotCount;
    private Integer totalSpotCount;

    public CountDto() {
    }

    public CountDto(Integer emptyCount, Integer totalCount) {
        this.emptySpotCount = emptyCount;
        this.totalSpotCount = totalCount;
    }

    public Integer getEmptySpotCount() {
        return emptySpotCount;
    }

    public void setEmptySpotCount(Integer emptySpotCount) {
        this.emptySpotCount = emptySpotCount;
    }

    public Integer getTotalSpotCount() {
        return totalSpotCount;
    }

    public void setTotalSpotCount(Integer totalSpotCount) {
        this.totalSpotCount = totalSpotCount;
    }
}
