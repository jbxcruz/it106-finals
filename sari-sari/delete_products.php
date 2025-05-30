<?php
include 'db.php';
header('Content-Type: application/json');

$id = $_POST['id'] ?? 0;
if ($id) {
    $stmt = $conn->prepare("DELETE FROM products WHERE id=?");
    $stmt->bind_param("i", $id);
    $ok = $stmt->execute();
    echo json_encode(["success" => $ok]);
    $stmt->close();
} else {
    http_response_code(400);
    echo json_encode(["success" => false, "error" => "Missing id"]);
}
$conn->close();
?>
