<?php

$db = (parse_ini_file("config.ini"));
$host = $db['host'];
$user = $db['user'];
$password = $db['password'];
$dbs = $db['db'];
$con = mysqli_connect($host,$user,$password,$dbs)  or die (mysqli_error());


if (isset($_GET['createOffer'])) {
   $con->query("INSERT INTO `offer`(`id_user`, `about`) VALUES ('". $_GET['user'] ."','". $_GET['text'] ."')");
}

if (isset($_GET['makeProposal'])) {
    $con->query("INSERT INTO `response`(`id_offer`, `id_user`, `res_text`) VALUES ('". $_GET['offer'] ."','". $_GET['user'] ."','". $_GET['text'] ."')");
}

if (isset($_GET['getOffers'])) {
    $result = $con->query("SELECT `id_offer`, `id_user`, `about`, `date` FROM `offer` WHERE active='t'");
    $all = $result->fetch_all(MYSQLI_ASSOC);

    header('Content-Type: application/json');
    echo json_encode($all);
}

if (isset($_GET['getOffer'])) {
    $result = $con->query("SELECT `id_user`, `about`, `date` FROM `offer` WHERE `active` = 't' AND `id_offer`='". $_GET['getOffer'] ."'");
    header('Content-Type: application/json');
    echo json_encode($result->fetch_assoc());
}

if (isset($_GET['getOfferByUser'])) {
    $result = $con->query("SELECT `id_offer`, `about`, `date` FROM `offer` WHERE `active` = 't' AND `id_user`='". $_GET['getOfferByUser'] ."'");
    header('Content-Type: application/json');
    echo json_encode($result->fetch_assoc());
}

if (isset($_GET['deactivateOffer'])) {
    $con->query("UPDATE `offer` SET `active`= 'f' WHERE `id_offer` ='". $_GET['deactivateOffer'] ."'");
}

if (isset($_GET['userInfo'])) {
    $result = $con->query("SELECT `name`, `lastname`, `tel`, `address` FROM `users` WHERE `id_user` = '". $_GET['userInfo'] ."'");
    header('Content-Type: application/json; charset=UTF-8');
    echo json_encode($result->fetch_assoc());
}

if (isset($_GET['proposalsForOffer'])) {
    $result = $con->query("SELECT `id_response`, `id_user`, `res_text` FROM `response` WHERE `id_offer` = '". $_GET['proposalsForOffer'] ."'");
    header('Content-Type: application/json');
    $all = $result->fetch_all(MYSQLI_ASSOC);
    echo json_encode($all);
}

if (isset($_GET['userOpinion'])) {
    $result = $con->query("SELECT * from `opinion` where `id_user` ='". $_GET['userOpinion'] ."'");

    header('Content-Type: application/json');
    echo json_encode($result->fetch_assoc());
}

if (isset($_GET['isActiveOffer'])) {
    $result = $con->query("SELECT * FROM `offer` WHERE `id_user` = '". $_GET['isActiveOffer'] ."' AND `active` = 't'");
        header('Content-Type: application/json');
    if (is_null($result->fetch_assoc())) {
        echo json_encode(array('active' => false));
    } else {
        echo json_encode(array('active' => true));
    }
}

if (isset($_GET['username'])) {
    $login = $_GET['username'];
    $pass = $_GET['password'];

    $result_pass = $con->query("SELECT `pass` from `users` where `login`='".$login."'");
    $pass_db = $result_pass->fetch_array();
    if (password_verify($pass, $pass_db['pass'])) {
        $result = $con->query("SELECT `id_user` from `users` where `login`='".$login."'");
        header('Content-Type: application/json');
        echo json_encode($result->fetch_assoc());
    }
}
