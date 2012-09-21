<?php 
$testString = '{
    "function": "order",
    "deviceID": "android3213089",
    "total": "234.89",
    "delivery": "true",
    "basket": [
        {
            "ItemName": "Hake",
            "Station": "A la Minute Grill",
            "quanitity": "3"
        },
        {
            "ItemName": "Beef Olives",
            "Station": "Main Meal",
            "quanitity": "2"
        },
        {
            "ItemName": "Chicken Lasagne & Veg",
            "Station": "Frozen Meals",
            "quanitity": "1"
        },
        {
            "ItemName": "Cannelloni Spinach & Feta (450gr)",
            "Station": "Frozen Meals",
            "quanitity": "1"
        },
        {
            "ItemName": "Cannelloni Spinach & Feta (200gr)",
            "Station": "Frozen Meals",
            "quanitity": "2"
        }
    ]
}';
		$jsonIterator = new RecursiveIteratorIterator(
    new RecursiveArrayIterator(json_decode($testString, TRUE)),
    RecursiveIteratorIterator::SELF_FIRST);

foreach ($jsonIterator as $key => $val) {
    if(is_array($val)) {
        echo "$key: \n <br />";
    } else {
        echo "$key => $val\n <br />";
    }
}

?>