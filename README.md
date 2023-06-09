# NICE-Coday-Competition-
Contains solution for NICE coday competition
 

NiceKart is an online shopping e-commerce platform that sells a wide variety of products.
As part of its advertising campaign, it has arranged a 5-day Shopping sale. 4 days of the sale has been already completed and NiceKart has seen an amazing response. It’s all geared up for the last day of the sale and it’s expecting an overwhelming response.
However, on similar sale done last year NiceKart had observed that their last day sale was not up to the mark as the in demand products were out of stock and resulted in low profit.
So this year NiceKart wants to ensure that based on the first 4 days of the sale, only those products that did sale well and generated high profits are made available in sufficient quantities.

This prediction for 5th days product quantities will be based on below 2 Criteria’s:
1.	Return on Investment (ROI) generated by the product.
2.	Average Sale rate (ASR) of the product over the first 4 days
Based on these parameters NiceKart would need to identify the total quantity to be made available for each of the products and the total warehouse capacity (sum of all product quantities) for same.

Below are the formulae to be used for the calculations:
1.	Return on Investment (ROI) for Product P = (Total profit (TP) earned in first 4 days for Product P / Total Investment (TI) on the Product P) *100

1.a Total profit (TP) earned in first 4 days for Product P = Sum of the Quantities sold for product P in first 4 days * (Margin on product P/100) * Cost price of product P
1.b Margin = [ (Sell Price – Cost Price) / Cost Price] * 100

1.c Total Investment (TI) on the Product P = Available quantity for the Product * Cost Price of the product

2.	Average Sale rate (ASR) of the Product P over the first 4 days = Average[(Day 1 Sale rate of Product P * 10%),(Day 2 Sale rate of Product P  * 20%),(Day 3 Sale rate of Product P * 30%),(Day 4 Sale rate of Product P * 40%)]
2.a Day X Sale rate for product P = (Quantity Sold for Product P on Day X/ Available Quantity for Product P) * 100
[Where X represents Day1 to 4)
3.	Day 5 prediction for Quantity of Product P as Per ROI = [ (ROI for Product P/10) * Available Quantity for product P] / 100 + Available Quantity for product P

4.	Day 5 prediction for Quantity of Product P as Per ASR = [(ASR for Product P * Available Quantity for product P) / 100] + Available Quantity for product P

5.	Final Day 5 prediction of Quantity for product P =  Average[Day 5 prediction for Quantity of Product P as Per ASR, Day 5 prediction for Quantity of Product P as Per ROI]

Note: Always consider Ceiling values for all decimal values returned by all the above calculations

Input Files (Resource files)
•	ProdcutInfo.csv
Contain the Product details including Sell price and available quantity
Csv Column name	Description	Example
ProductId	Unique identifier for the product	123
ProductName	Name of the product	Handmade Granite Chips
CostPrice	Purchase Price of the product	6000
SellPrice	Sell Price of the product after discounts	6500
AvailableQuantity	Stock available for the product for the day	50




•	Day_<X>_PurchaseHistory.csv
Contain the Order History for the day X
Csv Column name	Description	Example
PurchaseHistoryId	Order ID	123
ProductId	ID of the product	111
Quantity	Total sold Units	2
PricePerQuantity 	Price at which product was sold	1000
TimeOfTheDay	Transaction Time	10:20:08
Note: If TimeOfTheDay for any transaction is missing/blank, then it means that the product was just added to cart but not purchased.
There will be 4 such files Day_<X>_PurchaseHistory.csv for last 4 days


Output:
We expect the program to return Total Warehouse Capacity and List of products (product id, name and predicted quantity) to be made available for last days sale.
The output product list should be sorted in descending order of predicted quantity (i.e. product with highest predicted quantity for Day 5 should come first).
In case multiple products have same predicted quantity, the products should be sorted in Ascending order by product name.


Sample Output
 

Happy Coding!
