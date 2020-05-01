//express is the framework we're going to use to handle requests
const express = require('express')
//Create a new instance of express
const app = express()

//let middleware = require('./utilities/middleware')

const bodyParser = require("body-parser");
//This allows parsing of the body of POST requests, that are encoded in JSON
app.use(bodyParser.json())

//Obtain a Pool of DB connections.
const { Pool } = require('pg')
const pool = new Pool({
    connectionString: process.env.DATABASE_URL,
    ssl: {
        rejectUnauthorized: false,
    }
})

app.get("/hello", (req, res)=>{
    res.send({
        message: "Hello, you send a GET request"
    });
});

app.post("/hello", (req, res)=>{
    res.send({
        message: "Hello, you send a POST request"
    });
});


app.get("/params", (req, res)=>{
    if(req.query.name){
        res.send({
            //req.query is a reference to arguments in the POST body
            message:"Hello, "+req.query.name+"! You sent a GET request"
        });
    }else{
        res.status(400);
        res.send({
            message: "Missing required information"
        });
    }
});

app.post("/params", (req, res) => {
    if (req.body.name) {
        res.send({
            //req.body is a reference to arguments in the POST body
            message: "Hello, " + req.body.name + "! You sent a POST Request"
        })
    } else {
        res.status(400)
        res.send({
            message: "Missing required information"
        })
    }
})


app.get("/wait", (req, res) => {
    setTimeout(()=>{
        res.send({
            message:"Thanks for waiting"
        });
    }, 5000)
});

app.post("/addcourse", (req, res) => {
    //Parameters for the courses
    let id = req.body['id'];
    let shortdesc = req.body['shortdesc'];
    let longdesc = req.body['longdesc'];
    let prereqs = req.body['prereqs'];

    if (id && shortdesc && longdesc && prereqs) {
        db.none('INSERT INTO Courses VALUES($1, $2, $3, $4)', [id, shortdesc, longdesc, prereqs])
            .then(() => {
                //We successfully added the course, let the user know
                res.send({
                    success: true
                });
            }).catch((err) => {
                // log the error
            console.log(err);
            res.send({
                success: false
                error: err
            });
        });
    }
    else {
        res.send({
            success: false,
            input: req.body,
            error: "Missing required information"
        });
    }
});

app.get("/courses", (req, res) => {
    db.manyOrNone('SELECT * FROM Courses')
    //If successful, run function passed into. then()
        .then((data) => {
            res.send({
                success: true,
                names: data
            });
        }).catch((error) => {
        console.log(error);
        res.send({
          success: false,
          error: error
        })
    });
});




/*
 * This middleware function will respond to inproperly formed JSON in
 * request parameters.
 */
app.use(function(err, req, res, next) {

    if (err instanceof SyntaxError && err.status === 400 && "body" in err) {
        res.status(400).send({ message: "malformed JSON in parameters" });
    } else next();
})

/*
 * Return HTML for the / end point.
 * This is a nice location to document your web service API
 * Create a web page in HTML/CSS and have this end point return it.
 * Look up the node module 'fs' ex: require('fs');
 */
app.get("/", (req, res) => {
    //this is a Web page so set the content-type to HTML
    res.writeHead(200, {'Content-Type': 'text/html'});
    for (i = 1; i < 7; i++) {
        //write a response to the client
        res.write('<h' + i + ' style="color:blue">Hello World!</h' + i + '>');
    }
    res.end(); //end the response
});

/*
 * Serve the API documentation genertated by apidoc as HTML.
 * https://apidocjs.com/
 */
// app.use("/doc", express.static('apidoc'))

/* 
* Heroku will assign a port you can use via the 'PORT' environment variable
* To accesss an environment variable, use process.env.<ENV>
* If there isn't an environment variable, process.env.PORT will be null (or undefined)
* If a value is 'falsy', i.e. null or undefined, javascript will evaluate the rest of the 'or'
* In this case, we assign the port to be 5000 if the PORT variable isn't set
* You can consider 'let port = process.env.PORT || 5000' to be equivalent to:
* let port; = process.env.PORT;
* if(port == null) {port = 5000} 
*/
app.listen(process.env.PORT || 5000, () => {
    console.log("Server up and running on port: " + (process.env.PORT || 5000));
});