<?php
require_once 'db_connecting.php';
if( isset($_POST['submit']) ){
$name=$_POST['name'];
$company_name=$_POST['company_name'];
$salary=$_POST['salary'];

$query="INSERT INTO `test_db`(`name`, `company_name`, `salary`)
VALUES ($name,$company_name,$salary)";
$result = array();
if ($conn->query($query) == TRUE) {
    array_push($result, array(
        "value" => "insert information successfully"
    ));
} else {
    array_push($result, array(
        "value" => "failed to insert this information please try again later"
    ));
}
$conn->close();
echo json_encode(array("result"=>$result));
}