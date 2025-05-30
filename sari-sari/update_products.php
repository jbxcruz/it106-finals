<?php

// update_product.php
include 'db.php';
header('Content-Type: application/json');

$id          = $_POST['id']          ?? 0;
$name        = $_POST['name']        ?? '';
$description = $_POST['description'] ?? '';
$price       = $_POST['price']       ?? 0;

if ($id && $name && $description && $price) {
    $stmt = $conn->prepare(
      "UPDATE products SET name=?, description=?, price=? WHERE id=?"
    );
    $stmt->bind_param("ssdi", $name, $description, $price, $id);
    $ok = $stmt->execute();
    echo json_encode(["success" => $ok]);
    $stmt->close();
} else {
    echo json_encode(["success" => false, "error" => "Missing fields"]);
}
$conn->close();
