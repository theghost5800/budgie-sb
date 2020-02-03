# Configurable Test Service Broker

## Configuring the service broker behavior

### Request

Route: `PUT /v2/configuration`

Body: `BehaviorConfiguration` object in JSON format, which may have the following properties:

* `id`: string/uuid - the unique ID of the configuration.
* `sync`: integer - if set, an operation will be synchronous and will take the specified time in milliseconds. It is the default behavior and may be set if the user wants to test a scenario where the request timeouts. 
* `async`: integer - if set, an operation will be asynchronous and will take the specified time in milliseconds.  
For example: `async: 10` will result in asynchronous operations, lasting 10 milliseconds.
* `failCreate`: array of `FailConfiguration` objects - if set, it can enable failing create operation.
* `failUpdate`: array of `FailConfiguration` objects - if set, it can enable failing update operation.
* `failDelete`: array of `FailConfiguration` objects - if set, it can enable failing delete operation.
* `failBind`: array of `FailConfiguration` objects - if set, it can enable failing bind operation.
* `failUnbind`: array of `FailConfiguration` objects - if set, it can enable failing unbind operation.

`FailConfiguration` may have the following properties:
* `all`: boolean - if set to true, every operation will fail.
* `status`: integer - REQUIRED this is the http status code, which will be returned if any service instance match the fail condition.
* `serviceIds`: array of strings - instances with service offering IDs matching the specified in this array, will fail the operation.
* `serviceNames`: array of strings - instances with service offering names matching the specified in this array, will fail the operation.
* `planIds`: array of strings - instances with service plan IDs matching the specified in this array, will fail the operation.
* `planNames`: array of strings - instances with service plan names matching the specified in this array, will fail the operation.
* `instanceIds`: array of strings - instances with IDs matching the specified in this array, will fail the operation.  

Bear in mind the the arrays of IDs or names combine with AND strategy, which means that an instance needs to match one of the elements in each specified array in the `FailConfiguration`.  
Consider the following example:
```
    {
        "serviceNames": [
            "foo"
        ],
        "planNames": [
            "foo-a", "bar"
        ]
    }
```
This `FailConfiguration` specifies that a service instance, which has service offering name `foo` AND service plan name `foo-a` OR `bar` will fail the operation.
An instance, which has service offering 'foo' and a plan `baz` will go through standard operation.

## Fetching the service broker behavior

### Request

Route: `GET /v2/configuration`

Body: `BehaviorConfiguration` object (see the upper section).

## Resetting the service broker behavior

### Request

Route: `DELETE /v2/configuration`

This will cause the broker to fall back to normal execution, which means synchronous operations with no downtime.

## Configuration example

```
{
    "async": 5000,
    "failDelete": [
        {
            "status": 400,
            "serviceNames": [
                "foo", "bar"
            ],
            "planNames": [
                "1.1", "1.2"
            ]
        },
        {
            "status": 404,
            "instanceIds": [
                "05e09a19-8830-456a-b3d3-fb82feacbebe"
            ]
        }
    ],
    "failUnbind": [
        {
            "all": true,
            "status": 400
        }
    ]
}
```
This example specifies that the behavior of the service broker will be asynchronous and each request will take 5 seconds.
Deletion of service instances, which have service offering `foo` OR `bar` AND plan `1.1` OR `1.2` will fail with status code 400.
Deletion of service instance, which has an ID `05e09a19-8830-456a-b3d3-fb82feacbebe` will also fail with status code 404.
Unbinding of services will fail every time with status code 400.

## Adding new service offerings

### Request

Route: `PUT /v2/catalog`

The body should follow the `ServiceOffering` data structure,
which is specified in the Official [Open Service Broker API](https://github.com/openservicebrokerapi/servicebroker/blob/v2.15/spec.md).

Users can add new service offerings with new service plans and then configure operations on them. This is useful for clean automation tests.