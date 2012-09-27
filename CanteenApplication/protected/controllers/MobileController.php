<?php 

require_once 'HTTP/Request2.php';
require_once 'HTTP/Request2/Response.php';

class MobileController extends Controller{
	
	public $layout='column1';
	
	/**
	 * Declares class-based actions.
	 */
	public function actions()
	{
		return array(
				// page action renders "static" pages stored under 'protected/views/site/pages'
				// They can be accessed via: index.php?r=site/page&view=FileName
				'page'=>array(
						'class'=>'CViewAction',
				),
		);
	}
	
	/**
	 * This is the action to handle external exceptions.
	 */
	public function actionError()
	{
		if($error=Yii::app()->errorHandler->error)
		{
			if(Yii::app()->request->isAjaxRequest)
				echo $error['message'];
			else
				$this->render('error', $error); //need to send http response instead of rendering page
		}
	}
	
	/**
	 * Processes menu data and sends HTTP Response to Client
	 */
	public function  actionGetMenu()
	{
		$model = new Menu();
		
		//This is to get the authentication detail and verifies if the menu should be parsed back
		//To be implemented at a later stage
		//$authDetails = http_get_request_body();
		
		//retrieval of menu from database and generate json string
		$model->getMenu();
		
		//retrieval of JSON string
		//$json_string = json_encode(array("updated" => "false", 'menu:'=>$menuEntries));
		$json_string = $model->getJsonString();
		
		//sending an http response with the menu in JSON formate
		HttpResponse::status(200);
		HttpResponse::setContentType('application/json');
		HttpResponse::setData($json_string);
		HttpResponse::send();
	}
	
	public function actionPlaceOrders()
	{
		//need a model to hanle this function
		
		//retriveal of orders, decode the json and create a sql entry into the 'orders' table in the database
		$placedOrders_json = http_get_request_body();
		
	}
	
}

?>