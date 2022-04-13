<?php 
    $host = 'localhost';
    $host_user = 'root';
    $host_password = '';
    $host_db = 'bookstore';
    $conn = mysqli_connect($host, $host_user, $host_password, $host_db);

    $username =$_POST['username'];
    $password = $_POST['password'];
    // $sql = "SELECT * FROM tbl_users WHERE username = '$username' AND password = '$password';";

    // $sql = "SELECT * FROM tbl_order_history WHERE order_id IN (SELECT order_id FROM tbl_user_order WHERE user_id IN (SELECT user_id FROM tbl_users WHERE username = '$username' AND password ='$password'));";

    $sql = "SELECT tbl_books.*, tbl_order_history.book_count FROM tbl_books JOIN tbl_order_history ON tbl_books.book_id = tbl_order_history.book_id JOIN tbl_user_order ON tbl_order_history.order_id = tbl_user_order.order_id WHERE tbl_user_order.user_id IN (SELECT user_id FROM tbl_users WHERE username = '$username' AND password = '$password' );";
    $results = mysqli_query($conn, $sql);
    $user = array();
    if(mysqli_num_rows($results)>0){
        $user = mysqli_fetch_all($results, MYSQLI_ASSOC);
        $user["success"] = "true";
        echo json_encode($user); 
    } else {
        $user["success"] = "false";
        echo json_encode($user); 
    }
    mysqli_free_result($results);
    mysqli_close($conn)
;?>