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
				//$this->redirect(Yii::app()->user->returnUrl);
				$this->redirect((array('site/displayOrder')));
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
	
	public function actionDisplayOrder()
	{
		$model = new DisplayOrder();
		
		var_dump($_POST);
		
		//collect user input data
		if(isset($_POST['DisplayOrder']))
		{
			//$model->attributes=$_POST['OrdersForm'];
			$model->order_status = $_POST['DisplayOrder']['order_status'];
			$model->delivery_status = $_POST['DisplayOrder']['delivery_status'];
			//var_dump($_POST['DisplayOrder']['order_status']);
			//var_dump($_POST['DisplayOrder']['delivery_status']);			
			//die;
		}
		
		if(Yii::app()->request->isAjaxRequest){
			//echo 'ajax went through';
			$model->order_status = $_POST['DisplayOrder']['order_status'];
			$model->delivery_status = $_POST['DisplayOrder']['delivery_status'];
		
		}
		
		
		$model->result = $model->extractOrderFromDb();
		
		//var_dump($_POST);
		
		//display the orders form page
		$this->render('displayOrder',array('model'=>$model));
	}
	
	public function actionCompleteOrder()
	{
		$model = new CompletedOrder();
		
		$model->result = $model->extractOrderFromDb();
		
		$this->render('completeOrder',array('result'=>$model->result));
	}
	
	public function actionHowTo()
	{
		$this->render('howto');
	}
	
	public function actionMobileTest()
	{
		$this->render('mobileTest');
	}
	
	public function actionJsonTest()
	{
		$this->render('jsonTest');
	}
	
	public function actionCreateJson()
	{
		$this->render('createJson');
	}
}