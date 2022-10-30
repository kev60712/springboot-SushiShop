# springboot-SushiShop

## Project Overview:
The challenge is to build a simulated sushi shop server-side program that takes orders from the customers, processes the orders in parallel, shows and updates the order status.

The program should be built using the following frameworks/libraries/tools:

- Spring boot
- H2 database
- Maven/Gradle
- Any other libraries you feel you may need


## Project Architecture:
- I decided to use the Producer Customer pattern to implement SushiShop. Because in the design, I can make sure each chef can complete the order concurrency and it won't interfere with each other. Furthermore, we can ensure each order can only be done by one chef.
- PS. In order to calculate timeSpent for each sushi, I added one column "lastUpdatedAt" in sushi_order table.

![Project Architecture](https://user-images.githubusercontent.com/20982158/198888950-cda3d49e-51e7-41df-b9ff-a1540ad99997.png)
![Project Structure](https://user-images.githubusercontent.com/20982158/198890482-f950c58a-55f6-4e9c-869b-4cf3bd133fbf.png)

## Result:

#### 1. Take Orders by Request and process the orders in parallel.
![Take Orders by Request and process the orders in parallel](https://user-images.githubusercontent.com/20982158/198889088-122b6045-cd96-4c90-9fd8-37b47b2f93d3.png)


#### 2. Submitting an order ( Method: **POST /api/orders)
- ##### Sussess: 201 Created
![Submitting an order_Success](https://user-images.githubusercontent.com/20982158/198889145-de8abca3-b566-4bcd-bb15-c5f8421ccec7.png)

- ##### Fail: 404 Not Found
![Submitting an order_Fail](https://user-images.githubusercontent.com/20982158/198889149-8c15f4db-e3f7-4c47-b17a-64c719611b31.png)


#### 3. Cancelling an order (Method: **DELETE /api/orders/{order_id}**)
- ##### Success: 200 OK
![Cancel Order Success](https://user-images.githubusercontent.com/20982158/198889358-835403e7-ec31-4c00-9f41-ae82fbf2f032.png)
![Cancel Order](https://user-images.githubusercontent.com/20982158/198889300-8e7acb51-8d5f-4aeb-a260-abb1c6ae3e5c.png)

- ##### Fail: 404 Not Found
![Cancel Order fail](https://user-images.githubusercontent.com/20982158/198889455-d83033c1-94a4-4fac-8319-da0286c11f2c.png)

#### 4. Displaying orders by status (Method: **GET /api/orders/status**)
- ##### No Orders:
![isplaying orders by status_No_Orders](https://user-images.githubusercontent.com/20982158/198889422-e72fd9fe-08ff-4ba4-a0a7-59bae26445f4.png)

- ##### Have Orders:
![Displaying orders by status_have_orders](https://user-images.githubusercontent.com/20982158/198889223-a163ff8d-9858-49e8-aa31-adefd10fa192.png)


## Bonus:
#### 5. Pausing an order (Method: **PUT /api/orders/{order_id}/pause**)
- ##### Success: 200 OK
![Pause Order Success](https://user-images.githubusercontent.com/20982158/198889588-6dddd9bf-a0d1-4244-8666-16fe9ae5038b.png)

- ##### Fail: 404 Not Found
![Pause Order fail](https://user-images.githubusercontent.com/20982158/198889385-f742cee1-7f12-476f-9a6c-2404594f6682.png)


#### 6. Resuming an order (Method: **PUT /api/orders/{order_id}/resume**)
- ##### Success: 200 OK
![Resume_Order_Success](https://user-images.githubusercontent.com/20982158/198889258-ddda7202-d9ab-436e-8547-f9cd7a93aa53.png)

- #####  Fail: 404 Not Found
![Resume Order Fail](https://user-images.githubusercontent.com/20982158/198889330-afdea4d0-9b98-41bd-bf8a-0502ede7e512.png)

#### 7. Pause and Resume Order:
![Take Orders by Request and process the orders in parallel](https://user-images.githubusercontent.com/20982158/198889486-074f1aa5-9495-46ea-bca3-2ddf09385632.png)

## Details:
Requirements:
- Server
  - The server should start on port 9000

- DB
  - Use an embedded in-memory H2 database with the following SQL to initialize the database when the server starts:
  ```
        DROP TABLE IF EXISTS sushi; 
        CREATE TABLE sushi (  
            id INT AUTO_INCREMENT PRIMARY KEY,  
            name VARCHAR(30),  
            time_to_make INT DEFAULT NULL  
        );  

        DROP TABLE IF EXISTS sushi_order;  
        CREATE TABLE sushi_order (  
            id INT AUTO_INCREMENT PRIMARY KEY,  
            status_id INT NOT NULL,  
            sushi_id INT NOT NULL,  
            createdAt TIMESTAMP NOT NULL default CURRENT_TIMESTAMP  
        );  

        DROP TABLE IF EXISTS status;
        CREATE TABLE status (
            id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(30) NOT NULL
        );

        INSERT INTO sushi (name, time_to_make) VALUES
            ('California Roll', 30),
            ('Kamikaze Roll', 40),
            ('Dragon Eye', 50);

        INSERT INTO status (name) VALUES
            ('created'),
            ('in-progress'),
            ('paused'),
            ('finished'),
            ('cancelled');
    ```
- REST APIs
  - All the response bodies should include the following fields:
    - code: 0 for success, and any other integers for failures
    - msg: A meaningful message represents the result
    - Build the following REST APIs that accepts and returns JSON data:
  
  - Submitting an order (Method: POST /api/orders)
   ```
    Request body:
            {
              "sushi_name": "California Roll"
            }

    Response: 201
            {
              "order": {
                "id": 10,
                "statusId": 1,
                "sushiId": 1,
                "createdAt": 1582643059540
              },
              "code": 0,
              "msg": "Order created"
            }
    ```
    Only three chefs are available, thus only three orders can be processed at the same time
    When an order is submitted, the order record should be saved into database with status set to "created"
    When a chef is ready to process an order, the corresponding order record should be updated in the database with status set to "in-progress"
    The field "time_to_make" from sushi table represents how long it takes to make a specific kind of sushi. For example, a California Roll takes 30 seconds to make, thus a chef will be occupied for 30 seconds to finish making the sushi
    When an order is completed, the corresponding order record should be updated in the database with status set to "finished"

  - Cancelling an order 
    - Method: DELETE /api/orders/{order_id}
    - Path parameter: order_id
    ```
    Response: 200
            {
              "code": 0,
              "msg": "Order cancelled"
            }
    ```
    
      The chef who is working on making the ordered sushi should stop upon cancellation request
      The order should be updated in the database with status set to "cancelled"
    
    
   - Displaying orders by status
    - Method: GET /api/orders/status
    ```
    Response: 200
            {
              "in-progress": [
                {
                  "orderId": 4,
                  "timeSpent": 23
                },
                {
                  "orderId": 5,
                  "timeSpent": 21
                }
              ],
              "created": [
                {
                  "orderId": 6,
                  "timeSpent": 0
                }
              ],
              "paused": [
                {
                  "orderId": 2,
                  "timeSpent": 5
                }
              ],
              "cancelled": [
                {
                  "orderId": 3,
                  "timeSpent": 6
                }
              ],
              "completed": [
                {
                  "orderId": 1,
                  "timeSpent": 30
                }
              ]
            }
    ```

Bonus:
  - Pausing an order
    - Method: PUT /api/orders/{order_id}/pause
    - Path parameter: order_id
    ```
    Response: 200
            {
              "code": 0,
              "msg": "Order paused"
            }
    ```
    When an order needs to be paused, the chef must pause the progress of making the sushi until the order is resumed
    The order should be updated in the database with status set to "paused"

  - Resuming an order
    - Method: PUT /api/orders/{order_id}/resume
    - Path parameter: order_id
    ```
    Response: 200
            {
              "code": 0,
              "msg": "Order resumed"
            }
    ```
    When an order needs to be paused, the chef must pause the progress of making the sushi until the order is resumed
    The order should be updated in the database with status set to "paused"
    When a resuming order request is received, the chef should continue to process the order with high priority. A resumed order should only be processed base on the remaining processing time. For example, an order of California Roll is paused after 20 seconds since the order became in-progress, then it should take 10 more seconds to finish once resumed
    The order should be updated in the database with status set to "in-progress" when it is resumed and "finished" upon completion
