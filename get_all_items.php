<?php
require_once 'db_connecting.php';
$query
="select name,company_name,salary from test_db";
$r=mysqli_query($conn, $query);
$result=array();
while($row=mysqli_fetch_array($r)){
    array_push($result, array(
        "name"=>$row['name'],
        "company_name"=>$row['company_name'],
        "salary"=>$row['salary']
    ));
}
echo json_encode(array("result"=>$result));