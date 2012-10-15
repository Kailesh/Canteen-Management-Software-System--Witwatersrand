<?php
$this->pageTitle=Yii::app()->name . ' - Orders';
$this->breadcrumbs=array(
	'Orders',
);
?>

<?php
// The current page will refresh automatically every 15 seconds.
header("Refresh: 15;");
?>

<h1>Incoming Orders:</h1>
<hr />

<div class="form">

<?php $form=$this->beginWidget('CActiveForm', array(
	'id'=>'order-form',
	'enableAjaxValidation'=>true,
)); ?>

<div class ="table">
<table>
<tr>
<th>DeviceID</th>
<th>Name</th>
<th>Item</th>
<th> Quantity </th>
<th> Time Placed </th>
<th> Delivery Location </th>
<th> Status </th>
</tr>

<?php foreach($model->result as $order) :?>

<?php if($order['status']==='placed' ||  $order['status']==='Processing') : ?>	
<?php //$counter_string= 'item' . (string)$counter;?>
<tr>
	<td> <?php echo($order['deviceid'])?> 	 		</td>
	<td> <?php echo($order['name'])?> 				</td>
    <td> <?php echo($order['item'])?>		 		</td> 
	<td> <?php echo($order['quantity'])?> 			</td>
	<td> <?php echo($order['timeplaced'])?> 		</td>
	<td> <?php echo($order['delivery_location'])?> 	</td>
	<?php  $status = $order['status'] ?>
	<td> <?php
	if ($order["delivery_location"]!="-") {
		echo $form->radioButtonList($model,'order_status['. $order['U_id'].']',array('Processing'=>'Processing','Delivering'=>'Delivering'),
		array('onchange'=>CHtml::ajax(array('type'=>'POST', 'url'=>'','success'=>'function(data){$("body").html(data);}')),'separator'=>' | ','labelOptions'=>array('style'=>'display:inline')));
	}
	else {
		echo $form->radioButtonList($model,'order_status['.$order['U_id'].']',array('Processing'=>'Processing','Done'=>'Done'),
		array('onchange'=>CHtml::ajax(array('type'=>'POST', 'url'=>'','success'=>'function(data){$("body").html(data);}')),'separator'=>' | ','labelOptions'=>array('style'=>'display:inline')));
	}
	 ?>
    </td>
</tr>

<?php endif; ?>

<?php endforeach; ?>
</table>

</div>
<?php $this->endWidget(); ?>
</div>