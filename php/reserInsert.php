<?php

    $con = mysqli_connect("localhost", "root", "1234", "lets");
    mysqli_query($con,'SET NAMES utf8');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");


    $userId = $_POST["userId"];
    $reserName = $_POST["reserName"];
    $member = (int)$_POST["member"];
    $reserDate = $_POST["reserDate"];
    $startTime = $_POST["startTime"];
    $endTime = $_POST["endTime"];

    $statement = mysqli_prepare($con, "INSERT INTO reservation (user_id, reser_name, member,
                                            reser_date, start_time, end_time) VALUES (?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "ssisss",
                            $userId, $reserName, $member, $reserDate, $startTime, $endTime);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["user_id"] = $userId;
        $response["reser_name"] = $reserName;
        $response["member"] = $member;
        $response["reser_date"] = $reserDate;
        $response["start_time"] = $startTime;
        $response["end_time"] = $endTime;
    }

    echo json_encode($response);
?>
