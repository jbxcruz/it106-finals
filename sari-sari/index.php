<?php
include 'db.php';
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Sari-Sari Store v2</title>
  <link rel="stylesheet" href="style.css">
</head>
<body>
  <header><h1>ONLINE SARI-SARI STORE</h1></header>
  <main>
    <?php
    $res = $conn->query("SELECT id, name, description, price FROM products");
    $items = $res ? $res->fetch_all(MYSQLI_ASSOC) : [];
    if (empty($items)): ?>
      <p class="empty">No products found.</p>
    <?php else: ?>
      <div class="grid">

      <?php foreach ($items as $index => $p): ?>
        <div class="card">
          <!-- Display 1-based index, not the DB id -->
          <div class="product-id"><?= $index + 1 ?></div>
          <h2><?= htmlspecialchars($p['name']) ?></h2>
          <p class="description"><?= htmlspecialchars($p['description']) ?></p>
          <p class="price">₱<?= number_format($p['price'],2) ?></p>
        </div>
      <?php endforeach; ?>


      </div>
    <?php endif; ?>
  </main>
</body>
</html>
