<?php
// sari-sari/add_product.php
include 'db.php';

header("Content-Type: application/json");

$name = $_POST['name'] ?? '';
$description = $_POST['description'] ?? '';
$price = $_POST['price'] ?? 0;

if ($name && $description && $price) {
    $stmt = $conn->prepare("INSERT INTO products (name, description, price) VALUES (?, ?, ?)");
    $stmt->bind_param("ssd", $name, $description, $price);

    if ($stmt->execute()) {
        echo json_encode(["success" => true, "message" => "Product added."]);
    } else {
        echo json_encode(["success" => false, "message" => "Error adding product."]);
    }

    $stmt->close();
} else {
    echo json_encode(["success" => false, "message" => "Missing fields."]);
}
$conn->close();
?>
