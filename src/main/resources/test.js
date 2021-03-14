const request = require('request');

// request.post(
//     'http://localhost:8889/api/change/method/Brent',
//     { json: { key: 'value' } },
//     function (error, response, body) {
//         if (!error && response.statusCode === 200) {
//             console.log(body);
//         }
//     }
// );

request.get(
    'http://localhost:8889/api/get/result/0.5/2.5/30',
    { json: { key: 'value' } },
    function (error, response, body) {
        if (!error && response.statusCode === 200) {
            console.log(body);
        }
    }
);