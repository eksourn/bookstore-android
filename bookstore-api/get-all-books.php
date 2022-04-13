<?php 
    $host = 'localhost';
    $host_user = 'root';
    $host_password = '';
    $host_db = 'bookstore';
    $conn = mysqli_connect($host, $host_user, $host_password, $host_db);

    // $id = $_POST['id'];

    
    $sql = "SELECT * FROM tbl_books;";
    $results = mysqli_query($conn, $sql);
    $books = mysqli_fetch_all($results, MYSQLI_ASSOC);
    mysqli_free_result($results);
    $books["success"] = "true";
    echo json_encode($books); 
    mysqli_close($conn);

    
    
?>