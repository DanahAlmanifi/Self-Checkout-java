# Self-Checkout-System-java
A Java-based self-checkout system connected to a MySQL database. It allows customers to view products, select quantities, and complete purchases, while automatically updating inventory and sales data in real-time using JDBC.

## Features

### Customer:
- View available products
- Select and purchase items
- Receive a printed invoice with the total cost

### Store Owner:
- View total profit
- Identify the most purchased item
- Monitor inventory updates in MySQL in real-time

## Tech Stack

- Java (OOP)
- JDBC
- MySQL
- jGRASP

## What I Learned

- How to connect Java with MySQL using JDBC
- How to handle real-time updates to a database
- Writing object-oriented classes for inventory and invoice systems

## Setup

1. Create a MySQL database called `Invoice_List`
2. Add a table `Items` with columns: `itemID`, `name`, `quantity`, `price`, `soldCount`
3. Run the Java files in jGRASP or any IDE

## Screenshot
https://github.com/DanahAlmanifi/Self-Checkout-java/blob/main/screenshotjava.png
https://github.com/DanahAlmanifi/Self-Checkout-java/blob/main/screenshotsql.png


## Author

[Danah Almanifi] (www.linkedin.com/in/danahalmanifi)
