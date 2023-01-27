console.log(localStorage.getItem('tournament_id'));
let arr;
$.ajax({
  url: "http://51.250.97.3/api/tournament_grid",
  method: "post",
  dataType: "json",
  data: {tournament_id : localStorage.getItem('tournament_id')},
  // data: {tournament_id : 64},
  success: function (data) {
  (function (win, doc, $) {
    // win.TestData = data.push([[{name : 'misha'}]]);
    win.TestData
    console.log(data)
    // initializer
    $(".my_gracket").gracket({ src: data });
  })(window, document, jQuery);
    arr =data;
  },
  error: function (xhr, status) {
    console.log(xhr.status);
  },
});

$.ajax({
  url: "http://51.250.97.3/api/get_third",
  method: "post",
  dataType: "json",
  data: {tournament_id : localStorage.getItem('tournament_id')},
  // data: {tournament_id : 64},
  success: function (data) {
  (function (win, doc, $) {
    // win.TestData = data.push([[{name : 'misha'}]]);
    win.TestData
    console.log(data)
    // initializer
    $(".my_gracket_third").gracket({ src: data });
  })(window, document, jQuery);
    arr =data;
  },
  error: function (xhr, status) {
    console.log(xhr.status);
  },
});




// (function (win, doc, $) {
//     win.TestData =
    
//     [
        
//       [
        
//         [
//           {
//             name: "Erik Zettersten",
//           },
//           { name: "Andrew Miller"},
//         ],
//         [
//           { name: "James Coutry"},
//           { name: "Sam Merrill"},
//         ],
//         [
//           { name: "Anothy Hopkins" },
//           { name: "Everett Zettersten"},
//         ],
//         [
//           { name: "John Scott"},
//           { name: "Teddy Koufus" },
//         ],
//         [
//           { name: "Arnold Palmer" },
//           { name: "Ryan Anderson" },
//         ],
//         [
//           { name: "Jesse James"},
//           { name: "Scott Anderson" },
//         ],
//         [
//           { name: "Josh Groben" },
//           { name: "Sammy Zettersten"},
//         ],
//         [
//           { name: "Jake Coutry"},
//           {
//             name: "Spencer Zettersten"
//           },
//         ],
//       ],
//       [
//         [
//           { name: "Erik Zettersten" },
//           { name: "James Coutry"},
//         ],
//         [
//           { name: "Anothy Hopkins"},
//           { name: "Teddy Koufus"},
//         ],
//         [
//           { name: "Ryan Anderson"},
//           { name: "Scott Anderson" },
//         ],
//         [
//           { name: "Sammy Zettersten"},
//           { name: "Jake Coutry" },
//         ],
//       ],
//       [
//         [
//           { name: "Erik Zettersten" },
//           { name: "Anothy Hopkins"},
//         ],
//         [
//           { name: "Ryan Anderson"},
//           { name: "Sammy Zettersten"},
//         ],
//       ],
//       [
//         [
//           { name: "Erik Zettersten"},
//           { name: "Ryan Anderson" },
//         ],
//       ],
//       [[{ name: "Erik Zettersten"}]],
//     ];

//     // initializer
//     $(".my_gracket").gracket({ src: win.TestData });
//   })(window, document, jQuery);