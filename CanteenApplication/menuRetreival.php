<?php

//make SOAP request to MS SharePoint to retrieve the latest menu data
//maybe use _soapCall
$client = new SoapClient("http://thevault/vti_bin/lists.asmx?WSDL");
$result = $client->GetListItems(array('listname'=>"Today's Lunch"));

//assume the response will be an arry
$menuData = $result->GetListItemsResponse->GetListItemsResult;

//open canteen system database
$user_name = "root";
$password = "zaq123";
$database = "canteenSystem";
$server = "127.0.0.1";

$db_handle = mysql_connect($server, $user_name, $password);

$db_found = mysql_select_db($database,$db_handle);

if ($db_found) {
	print "Database Found";
	//erase all menu entries from the menu table within the canteen system
	$sql_truncate = "TRUNCATE TABLE canteenSystem";
	mysql_query($sql_truncate,$db_handle);

	//now we can try and access each menu data in the response array
	foreach ($menu as $menuItem)
	{
		$station = $menuItem['ows_LinkTitle'];
		$item = $menuItem['ows_Item'];
		$price = $menuItem['ows_Price'];

		//now write stuff into database
		//populate with the newest menu data (i.e. insert PARAMS into menu)
		$sql_insert = "INSERT INTO menu (station,item,price,availability) VALUES('" . $station . "','" . $item . "','" . $price . "',true)";
		mysql_query($sql_insert);
	}

}

else {
	print "Database NOT Found";
}

//closing the database
mysql_close($db_handle);
?>