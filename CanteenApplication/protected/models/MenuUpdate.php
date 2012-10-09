<?php 
class MenuUpdate
{
	private $status;
	
	public function getMenuStatus()
	{
		$this->status = false;
		//return $this->status;
	}
	
	public function encodeResponse()
	{
		return json_encode(array('menuUpdated'=>$this->status));
	}
}
?>