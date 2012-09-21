<?php 

require_once 'HTTP/Request2/Response.php';

//echo "Hello there you are now connected to the canteen web server <br />";

/*HttpResponse::status(200);
HttpResponse::setContentType('json');
//HttpResponse::setHeader('From', 'Lymber');
HttpResponse::setData($_POST);
HttpResponse::send();*/

$connection = Yii::app()->db;

$sql_select = 'SELECT *   FROM menu';

$command = $connection->createCommand($sql_select);

$menuData = $command->query();

$row = $menuData->readAll();

$json_string = json_encode(array("updated" => "false", 'menu:'=>$row));

$headers = http_get_request_headers();
$result = http_get_request_body();

$decodeResult = json_decode($result);

HttpResponse::status(200);
HttpResponse::setContentType('application/json');
//HttpResponse::setHeader('From', 'Lymber');
HttpResponse::setData($json_string);
HttpResponse::send();

flush();

?>