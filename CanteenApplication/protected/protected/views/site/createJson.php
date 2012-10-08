<?php 

$connection = Yii::app()->db;

$sql_select = 'SELECT *   FROM menu';

$command = $connection->createCommand($sql_select);

$menuData = $command->query();

$row = $menuData->readAll();

$json_string = json_encode(array("updated" => "false", 'menu:'=>$row));

echo $json_string;

/*
while ($row = $menuData->read()!==false)
{
	$station = $row['station'];
	$item = $row['item'];
	$price = $row['price'];
	$availability = $row['availability'];
	
	echo "$station - $item - $price - $availability <br /> \n";
}*/

//var_dump($menuData);

/*$menu = array();

echo "{ \n <br />";
echo "\"menu\":[ \n <br />";
foreach($menuData as $menuEntries) 
{ 
	echo "{ \n <br />";
	foreach($menuEntries as $station=>$item)
	{
		echo "$station --- $item <br />\n";
	}
	echo "}, \n <br />";	  
}
echo " \n <br />";
echo "} \n <br />";
*/


/*$rs=array();
foreach($menuData as $key => $value){
	//process each item here
	echo "Station $key is $value <br /> \n";
}*/

/*$arr = array("one", "two", "three");
reset($arr);

foreach ($arr as $key => $value) {
	echo "Key: $key; Value: $value<br />\n";
}*/

//$comma_separated = implode(",", $menuData);

//echo $comma_separated; // lastname,email,phone*/

//echo $menuData;

//echo 'hello';


?>