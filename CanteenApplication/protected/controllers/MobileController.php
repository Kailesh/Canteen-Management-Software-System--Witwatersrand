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
		//need a model to handle this function
		$model = new PlaceOrder();
		
		//retriveal of orders, decode the json and create a sql entry into the 'orders' table in the database
		$placedOrders_json = http_get_request_body();
		//$basket = array(array("item"=>"Hake", "station"=>"A la Minute Grill", "quantity"=>2),array("item"=>"Beef Olives", "station"=>"Main Meal", "quantity"=>3));
	//	$placedOrders_json = json_encode(array("deviceID"=>"90:C1:15:BC:97:4F","name"=>"kailesh","total"=>"60.50","deliveryLocation"=>"Floor 3 - Kitchen Side", "orderNumber"=>1,
//"basket"=>$basket)); 
		
		//var_dump($placedOrders_json);
		//die;
		//pass the order in json format to the model
		$model->decodePlacedOrder($placedOrders_json);
		//var_dump($model->placedOrder);
		//now call the function that will write the order into the database
		$model->storeOrderIntoDB();		
		
		HttpResponse::status(200);
		HttpResponse::setContentType('text/HTML');
		HttpResponse::setData("Orders Recieved");
		HttpResponse::send();
		
	}
	
	public function actionAuthenticate()
	{
		$model = new Authenticate();
		
		$authenticate_json = http_get_request_body();
		//$authenticate_json = json_encode(array("username"=>"kailesh","password"=>"1c3dd8b850b055bb7b6fb0fb59a7cd4","deviceID"=>"90:C1:15:BC:97:4F"));
		$model->verifyDetails($authenticate_json);
		
		$verification = $model->getAccess();
		$responseMsg = $model->generateJsonResponse();
		//var_dump($responseMsg);
		/*HttpResponse::status(200);
		HttpResponse::setContentType('text/HTML');
		HttpResponse::setData("Orders Recieved");
		HttpResponse::send();*/
		
		//var_dump($responseMsg);
		
		if($verification===true)
		{
			HttpResponse::status(200);
			HttpResponse::setContentType('application/json');
			HttpResponse::setData($responseMsg);
			HttpResponse::send();
		}
		else
		{
			HttpResponse::status(200);
			HttpResponse::setContentType('application/json');
			HttpResponse::setData($responseMsg);
			HttpResponse::send();
		}
	}
	
	public function actionvideoFeed(){
		
		$model = new VideoFeed();
		
		$image= $model->getVideoFeed();
		
		HttpResponse::status(200);
		HttpResponse::setContentType('image/JPEG');
		HttpResponse::setData($image);
		HttpResponse::send();
	}
	
	public function actionQueryProgress()
	{
		$model = new QueryProgress();
		
		$request = http_get_request_body();
		//$request = json_encode(array("deviceID"=>"56:78:3D:E5:8F:N1","order"=>1));
		
		//var_dump($request);
		
		$model->decodeStatusRequest($request);
		$model->getStatusOrder();
		$response = $model->encodeMsg(); 
		//var_dump($response);
		HttpResponse::status(200);
		HttpResponse::setContentType('application/json');
		HttpResponse::setData($response);
		HttpResponse::send();
		
	}
	
	public function actionMenuUpdate()
	{
		$model = new MenuUpdate();
		$model->getMenuStatus();
		$encode_response = $model->encodeResponse();
		
		HttpResponse::status(200);
		HttpResponse::setContentType('application/json');
		HttpResponse::setData($encode_response);
		HttpResponse::send();
	}
	
	public function actionLongPoller()
	{
		$model = new LongPoller();
		
		//$request = http_get_request_body();
		$request = json_encode(array("deviceID"=>"90:C1:15:BC:97:4F","orderNo"=>"1")); 
		
		$model->decodeStatusRequest($request);
		$model->checkStatus();
		$responseMsg = $model->generateResponse();
		
		HttpResponse::status(200);
		HttpResponse::setContentType('application/json');
		HttpResponse::setData($responseMsg);
		HttpResponse::send();
	}
	
}

?>