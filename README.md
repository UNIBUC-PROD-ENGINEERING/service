# Fake Emag

Java Springboot API which mimics the functionalities of the romanian site [Emag](https://www.emag.ro/).

# Features

The purpose is to simulate a highly-available application with diverse components (buying, support, products).

- *User Authentication & Authorization* – Secure login and registration using JWT-based authentication.  
- *Product Catalog* – Browse and search for products with category-based organization.  
- *Shopping Cart & Checkout* – Add products to a cart, update quantities, and proceed to a secure checkout process.  
- *Order Management* – Track order history and status.  
- *Support Tickets* – Submit tickets in regards to buying experience or products.

# Arhitecture and infrastructure

The application will consist of a Java Springboot API containerized with Docker. We will have the backend app duplicated across multiple instances in a cluster that is accessed through a load balancer, and also feature a database server. Cluster orchestration will be done with kubernetes which will also allow us to deploy monitoring containers inside the environment and ease container communication. Each cluster node will have one monitoring container with multiple ( hopefully because of traffic ) app containers.

# Pull Requests

PR's should have approvall from at least one other member before merging, and also should pass testing pipelines.