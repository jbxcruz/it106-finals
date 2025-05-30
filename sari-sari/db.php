<?php
// db.php
$host = 'localhost';
$user = 'root';
$pass = '';
$db   = 'sari_sari_db';  // your database name

$conn = new mysqli($host, $user, $pass, $db);
if ($conn->connect_error) {
    header('Content-Type: application/json');
    http_response_code(500);
    die(json_encode([
        "success" => false,
        "error"   => "DB connection failed: " . $conn->connect_error
    ]));
}
?>
