@Post
endpoint->
localhost:9090/drools/create-rule

request Body ->

{
    "data": [
        {
            "name": "rule1",
            "object": "Product",
            "salienceNumber": "100",
            "conditions": [
                {
                    "property": "Owner",
                    "value": "Bruno Booth",
                    "operator": "EQUAL_TO",
                    "datatype": "string"
                },
                {
                    "property": "Type",
                    "value": "Italian",
                    "operator": "EQUAL_TO",
                    "datatype": "string"
                }
            ],
            "actions": [
                {
                    "action": "Region",
                    "actionarg": "Region-77"
                },
                {
                    "action": "State",
                    "actionarg": "CA"
                },
                {
                    "action": "IsUpdated",
                    "actionarg": "Yes"
                }
            ]
        },
        {
            "name": "rule2",
            "object": "Product",
            "salienceNumber": "500",
            "conditions": [
                {
                    "property": "Group",
                    "value": "Group-1",
                    "operator": "EQUAL_TO",
                    "datatype": "string"
                }
            ],
            "actions": [
                {
                    "action": "Owner",
                    "actionarg": "Raj Singh"
                },
                {
                    "action": "IsUpdated",
                    "actionarg": "Yes"
                }
            ]
        }
    ]
}


__________________________________________________________________


@post
endpoint->
localhost:9090/drools/execute-rule?path=src/main/resources/csv/TestData2.csv

Request Body->
{
    "data": [
        {
            "name": "rule1",
            "object": "Product",
            "salienceNumber": "100",
            "conditions": [
                {
                    "property": "Owner",
                    "value": "Bruno Booth",
                    "operator": "EQUAL_TO",
                    "datatype": "string"
                },
                {
                    "property": "Type",
                    "value": "Italian",
                    "operator": "EQUAL_TO",
                    "datatype": "string"
                }
            ],
            "actions": [
                {
                    "action": "Region",
                    "actionarg": "Region-77"
                },
                {
                    "action": "State",
                    "actionarg": "CA"
                },
                {
                    "action": "IsUpdated",
                    "actionarg": "Yes"
                }
            ]
        },
        {
            "name": "rule2",
            "object": "Product",
            "salienceNumber": "500",
            "conditions": [
                {
                    "property": "Group",
                    "value": "Group-1",
                    "operator": "EQUAL_TO",
                    "datatype": "string"
                }
            ],
            "actions": [
                {
                    "action": "Owner",
                    "actionarg": "Raj Singh"
                },
                {
                    "action": "IsUpdated",
                    "actionarg": "Yes"
                }
            ]
        }
    ]
}