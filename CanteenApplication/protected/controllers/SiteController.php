<?php

require_once 'HTTP/Request2.php';
require_once 'HTTP/Request2/Response.php';

class SiteController extends Controller
{
	public $layout='column1';

	/**
	 * Declares class-based actions.
	 */
	public function actions()
	{
		return array(
			// captcha action renders the CAPTCHA image displayed on the contact page
			'captcha'=>array(
				'class'=>'CCaptchaAction',
				'backColor'=>0xFFFFFF,
			),
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
	        	$this->render('error', $error);
	    }
	}
	
	/**
	 * Displays the index page
	 */
	public function actionIndex()
	{
		// renders the view file 'protected/views/site/index.php'
		// using the default layout 'protected/views/layouts/main.php'
		$this->render('index');
	}
	
	
	/**
	 * Displays the contact page
	 */
	public function actionContact()
	{
		$model=new ContactForm;
		if(isset($_POST['ContactForm']))
		{
			$model->attributes=$_POST['ContactForm'];
			if($model->validate())
			{
				$headers="From: {$model->email}\r\nReply-To: {$model->email}";
				mail(Yii::app()->params['adminEmail'],$model->subject,$model->body,$headers);
				Yii::app()->user->setFlash('contact','Thank you for contacting us. We will respond to you as soon as possible.');
				$this->refresh();
			}
		}
		$this->render('contact',array('model'=>$model));
	}

	/**
	 * Displays the login page
	 */
	public function actionLogin()
	{
		$model=new LoginForm;

		// if it is ajax validation request
		if(isset($_POST['ajax']) && $_POST['ajax']==='login-form')
		{
			echo CActiveForm::validate($model);
			Yii::app()->end();
		}

		// collect user input data
		if(isset($_POST['LoginForm']))
		{
			$model->attributes=$_POST['LoginForm'];
			// validate user input and redirect to the previous page if valid
			if($model->validate() && $model->login())
				$this->redirect(Yii::app()->user->returnUrl);
		}
		// display the login form
		$this->render('login',array('model'=>$model));
	}

	/**
	 * Logs out the current user and redirect to homepage.
	 */
	public function actionLogout()
	{
		Yii::app()->user->logout();
		$this->redirect(Yii::app()->homeUrl);
	}
	
	public function actionTest() {
	
		$model=new Test;
	
		/* Code for validation and redirect upon save. */
		if(isset($_POST['Test']))
		{
			// $dummy = $_POST['TestForm'];
			// $model->attributes = $dummy;
			//$model->validate();
			$model->data = $_POST['Test']['data'];
			//var_dump($model->data); die();
			
			if($model->validate())
			{
				//echo 'That is good';
				//var_dump($response); die();
				$request = new HTTP_Request2("http://146.141.125.89/yii/index.php?r=site/test/data={$model->data}", HTTP_Request2::METHOD_GET);
				$response = $request->send();//new HTTP_Request2_Response($request);
				echo "Response status: " . $response->getStatus() . "\n";
				
				try {
					if (200 == $response->getStatus()) {
						$model->result = 'you got end to end to work!!';
					} else {
						echo 'Unexpected HTTP status: ' . $response->getStatus() . ' ' .
								$response->getReasonPhrase();
					}
				} catch (HTTP_Request2_Exception $e) {
					echo 'Error: ' . $e->getMessage();
				}
			}
	
	
			//var_dump($model->data); die();
		}
	
	
		// If not saved, render the create View:
		$this->render('testView',array(
				'model'=>$model, // Model is passed to create.php View!
		));
	}
	
	public function actionMobileTest()
	{
		$this->render('mobileTest');
	}
}