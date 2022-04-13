<?php
    $host = 'localhost';
    $host_user = 'root';
    $host_password = '';
    $host_db = 'bookstore';
    $conn = mysqli_connect($host, $host_user, $host_password, $host_db);

    $sql = "";
    foreach(array_keys($_POST) as $key){
        $value = $_POST[$key];
        if($key=="author_name"){
            $sql = "SELECT * FROM tbl_books WHERE book_id IN (SELECT book_id FROM tbl_author_books WHERE author_id IN (SELECT author_id FROM tbl_is_author WHERE author_name LIKE '%$value%'));";
        } else {
            $sql = "SELECT * FROM tbl_books WHERE $key LIKE '%$value%';";
        }
    }

    $result = mysqli_query($conn, $sql);
    if(mysqli_num_rows($result) > 0){
        $books = mysqli_fetch_all($result, MYSQLI_ASSOC);
        $books["success"] = "true";
        echo json_encode($books); 
    }else{
        $books["success"] = "false";
        echo json_encode($books);
    }
    mysqli_free_result($result);
    mysqli_close($conn);
?>