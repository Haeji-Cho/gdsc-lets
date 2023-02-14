<?php

    $con = mysqli_connect("localhost", "root", "1234", "lets");
    mysqli_query($con,'SET NAMES utf8');

    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");

    $userId = $_POST["userId"];
    $nickname = $_POST["nickname"];
    $address = $_POST["address"];
    $favSpo = $_POST["favSpo"];
    $role = (int)$_POST["role"];
    $group = (int)$_POST["group"];

    $statement = mysqli_prepare($con, "UPDATE user SET nickname = ?, address = ?,
                               favorite_sport = ?, role = ?, is_group = ? WHERE user_id = ?");
    mysqli_stmt_bind_param($statement, "sssiis", $nickname, $address, $favSpo, $role, $group, $userId);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);

    $response = array();
    $response["success"] = false;


    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["nickname"] = $nickname;
        $response["address"] = $address;
        $response["favSpo"] = $favSpo;
        $response["role"] = $role;
        $response["group"] = $group;
        $response["userId"] = $userId;
    }

    echo json_encode($response);
    mysqli_close($con);
?>