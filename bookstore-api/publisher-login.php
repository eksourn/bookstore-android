<?php
  $host = 'localhost';
  $host_user = 'root';
  $host_password = '';
  $host_db = 'bookstore';
  $conn = mysqli_connect($host, $host_user, $host_password, $host_db);

  $username =$_POST['pub_username'];
  $password = $_POST['pub_password'];

  $sql = "SELECT * FROM tbl_books WHERE publisher_name = (SELECT pub_name FROM tbl_publishers WHERE pub_username='$username' AND pub_password='$password');";
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


?>