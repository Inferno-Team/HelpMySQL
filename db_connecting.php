<?php
$host = "localhost";
$password = "";
$userName = "root";
$dataBaseName = "testdb";
$conn = mysqli_connect($host,$userName, $password, $dataBaseName)
   or die('Error in connect to server');