# Documentation:

## USER:
`/wigellrepairs`

### Get services:
### [GET]
`/services`

### Booking:
### [POST]
`/bookservice`
```json
{
    "date": <epochseconds>,
    "service": {
        "id": <id>
    }
}
```

### Cancel: [PUT]
`/cancelbooking`
```json
{
    "id": <id>
}
```

### Get bookings: [GET]
`/mybookings`

## Admin:
`/wigellrepairs`

### List Canceled: [GET]
`/listcanceled`

### List Upcoming: [GET]
`/listupcoming`

### List Past: [GET]
`/listpast`

### List technicians: [GET]
`/technicians`

### Add service: [POST]
`/addservice`
```json
{
    "name": "Name of service",
    "category": "<MECHANIC/ELECTRONICS/APPLIANCES>",
    "technicians": [
        <optional>
        {
            "id": <id>
        }
    ]
}
```

### Update service: [PUT]
`/updateservice`
```json
{
    "id": <id>
    "name": "Name of service",
    "category": "<MECHANIC/ELECTRONICS/APPLIANCES>"
    "technicians": [
        <optional>
        {
            "id": <id>
        }
    ]
}
```

### Remove service: [DELETE]
`/remservice/<id>`

### Add technician: [POST]
`/addtechnician`
```json
{
    "name": "Full name",
    "expertise": "<MECHANIC/ELECTRONICS/APPLIANCES>"
}
```

### List technicians: [GET]
`/technicians`

## dockbuild.sh
> [!IMPORTANT]
> Requires `docker` and `docker-buildx`

e.g `./dockbuild.sh -rn services-network -p 5555 -N wigellrepairs`
> [!INFO]
```
 Options:
 -i <name>                 image name, if left out: prints name of current dir
 -t <testing>              tag, if left out: "latest"
 -s <dir>                  source, if left out: "."
 -r                        Run after successful build
 -d                        Run in background (detach)
 -n <network>              Network name
 -N <name>                 instance name
 -p <port>/<local>:<cont>  Port
```
