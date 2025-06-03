<?php
error_reporting(E_ALL & ~E_NOTICE & ~E_WARNING);
include 'db.php';
header('Content-Type: application/json');

$id = $_POST['id'] ?? 0;
if (!$id) {
    http_response_code(400);
    echo json_encode([
        "success" => false,
        "error"   => "Missing id"
    ]);
    exit;
}

$stmt = $conn->prepare("DELETE FROM products WHERE id = ?");
$stmt->bind_param("i", $id);
$stmt->execute();

if ($stmt->affected_rows > 0) {
    echo json_encode([ "success" => true ]);
} else {
    http_response_code(400);
    echo json_encode([
        "success" => false,
        "error"   => "ID not found"
    ]);
}

$stmt->close();
$conn->close();
