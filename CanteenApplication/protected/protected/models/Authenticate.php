<?php 

class Authenticate{
	
	public $user;
	private $access;
	private $accountBalance;
	private $reason;
	
	public function verifyDetails($details)
	{
		$decode_details = json_decode($details);
		//var_dump($decode_details);
		
		$username = $decode_details->username;
		//var_dump($username);
		
		$databaseConnection = Yii::app()->db;
		
		$sql_select = "SELECT *   FROM authentication WHERE username = '".$username."'";
		
		$command = $databaseConnection->createCommand($sql_select);
		
		$result = $command->query();
		
		$this->user = $result->read();
		
		//var_dump($this->user['password']);
		//var_dump($decode_details->password);
		
		if ($decode_details->password===$this->user['password'])
		{
			$this->access=true;
			$this->reason='RMB-OK';
		}
		else
		{
			$this->access=true;
			$this->reason='RMB-01';
		}
	}
	
	public function getAccess()
	{
		return $this->access;
	}
	
	public function generateJsonResponse()
	{
		$json_string = json_encode(array("access"=>$this->access, "reason"=>$this->reason, "balance"=>$this->user['credit']));
		return $json_string;
	}
	
}

?>