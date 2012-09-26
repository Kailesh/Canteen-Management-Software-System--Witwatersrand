<?php 

/**
 * Menu Class
 * Menu is extracted from the database and possibly convert into JSON format?
 */

class Menu{
	
	public $dataBaseConnection;
	public $sqlCommand;
	
	public function getMenu()
	{
		//connect to database
		$dataBaseConnection = Yii::app()->db;
		//generate sql select query
		$sql_select = 'Select * FROM menu';
		//create the sql command
		$sqlCommand = $dataBaseConnection->createCommand($sql_select);
		//execute the query command
		$menuData = $sqlCommand->query();
		//read all the data from the query
		$menuEntries = $menuData->readAll();
		//return data to controller
		return $menuEntries;
	}
}

?>