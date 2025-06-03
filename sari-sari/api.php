<?php
// api.php
header('Content-Type: application/json');
header('Access-Control-Allow-Origin: *');
header('Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS');
header('Access-Control-Allow-Headers: Content-Type');
if ($_SERVER['REQUEST_METHOD'] === 'OPTIONS') {
    http_response_code(200);
    exit();
}




include 'db.php';
$method = $_SERVER['REQUEST_METHOD'];



switch ($method) {

    
    case 'GET':
        if (isset($_GET['id'])) {
            $id = intval($_GET['id']);
            $stmt = $conn->prepare("SELECT id, name, description, price FROM products WHERE id=?");
            $stmt->bind_param("i", $id);
            $stmt->execute();
            $res = $stmt->get_result();
            if ($row = $res->fetch_assoc()) {
                echo json_encode($row);
            } else {
                http_response_code(404);
                echo json_encode(["success"=>false,"error"=>"Not found"]);
            }
            $stmt->close();
        } else {
            $res = $conn->query("SELECT id, name, description, price FROM products");
            $list = [];
            while ($r = $res->fetch_assoc()) {
                $list[] = $r;
            }
            echo json_encode($list);
        }
        break;




        
    case 'POST':
        $data = json_decode(file_get_contents('php://input'), true);
        if (!empty($data['name']) && !empty($data['description']) && isset($data['price'])) {
            $stmt = $conn->prepare(
              "INSERT INTO products (name, description, price) VALUES (?, ?, ?)"
            );
            $stmt->bind_param("ssd",
                $data['name'],
                $data['description'],
                $data['price']
            );
            $ok = $stmt->execute();
            echo json_encode(["success"=>$ok, "id"=>$stmt->insert_id]);
            $stmt->close();
        } else {
            http_response_code(400);
            echo json_encode(["success"=>false,"error"=>"Missing fields"]);
        }
        break;





    case 'PUT':
        $data = json_decode(file_get_contents('php://input'), true);
        if (!empty($data['id']) && !empty($data['name']) && !empty($data['description']) && isset($data['price'])) {
            $stmt = $conn->prepare(
              "UPDATE products SET name=?, description=?, price=? WHERE id=?"
            );
            $stmt->bind_param("ssdi",
                $data['name'],
                $data['description'],
                $data['price'],
                $data['id']
            );
        $stmt->execute();
        if ($stmt->affected_rows > 0) {
            echo json_encode(["success" => true]);
        } else {
            http_response_code(400);
            echo json_encode(["success" => false, "error" => "ID not found"]);
        }
        $stmt->close();

        } else {
            http_response_code(400);
            echo json_encode(["success"=>false,"error"=>"Missing fields"]);
        }
        break;




        // Example for api.php DELETE branch
        case 'DELETE':
            $data = json_decode(file_get_contents("php://input"), true);
            if (!empty($data['id'])) {
                $stmt = $conn->prepare("DELETE FROM products WHERE id=?");
                $stmt->bind_param("i", $data['id']);
                $stmt->execute();
                if ($stmt->affected_rows > 0) {
                    echo json_encode(["success" => true]);
                } else {
                    http_response_code(400);
                    echo json_encode(["success" => false, "error" => "ID not found"]);
                }
                $stmt->close();
            } else {
                http_response_code(400);
                echo json_encode(["success" => false, "error" => "Missing id"]);
            }
            break;





    default:
        http_response_code(405);
        echo json_encode(["success"=>false,"error"=>"Method not allowed"]);
        break;
}

$conn->close();
?>
