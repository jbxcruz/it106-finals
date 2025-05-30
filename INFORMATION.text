note:



javaForStore = is responsible for the “Java Application” entity in the spec.

Contents
src/ — your Java source files:
Main.java — the console-menu UI that drives all four CRUD actions.
ApiClient.java — low-level HTTP code (GET, POST, PUT, DELETE) talking to your PHP API.
Product.java — your data model class, matching the columns in your database table.
libs/gson-2.10.1.jar — the external JSON library you use to serialize/deserialize Product objects.
bin/ — (generated at compile time) all the .class files that the JVM runs.

Purpose
Presents the text-based menu (Add, View, Update, Delete, Exit).
Gathers user input, validates it, turns it into HTTP calls.
Parses JSON responses back into Java objects and prints them.
Fulfills the “Java Application” requirement: it’s a standalone client that communicates with your web server over HTTP.






sari-sari = is the Web server + Front-end, fulfilling both the “Webserver” and “Webpage” parts of the spec.


Contents
db.php — your shared MySQL connection bootstrap (hostname, credentials, error handling).

api.php (or separate products.php / add_product.php / update_product.php / delete_product.php) — the RESTful endpoint(s) that:
Read from the database (SELECT)
Insert new rows (INSERT)
Update existing rows (UPDATE)
Delete rows (DELETE)
All with prepared statements for safety and CORS headers so your Java client can call them.

index.php — a simple storefront page (HTML + PHP) that.
Includes db.php
Fetches all products and renders them in a styled grid
Now shows a sequential number (1,2,3…) and the product data in cards
style.css — the CSS that makes your storefront look like a modern “Sari-Sari Store” page.
testphp.php — a throwaway file (often just phpinfo()) you used to verify PHP/Apache were working.





Implements the “Web server” that receives HTTP requests from your Java app and manipulates the SQL database.

Implements the “Webpage” requirement, even though users don’t interact with it directly for CRUD: it’s there to show data in a browser.

Connects to your SQL database (the products table), which holds all the persistent state for your inventory.





