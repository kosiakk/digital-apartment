/**
 * Created by Alexander Kosenkov on 17.09.2016.
 */

var delay = 1000;

function refr() {

    var xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        if (xhr.readyState == 4 && xhr.status == 200) {

            var fetched = xhr.response;
            if (fetched) {

                var updateEl = document.createElement('html');
                updateEl.innerHTML = fetched;

                var bodyNew = updateEl.getElementsByClassName("online-update");
                var bodyOld = document.getElementsByClassName("online-update");
                for (var i = 0; i < bodyNew.length; i++) {
                    morphdom(bodyOld[i], bodyNew[i]);
                }

                bodyNew = updateEl.getElementsByClassName("online-update-style");
                bodyOld = document.getElementsByClassName("online-update-style");
                for (i = 0; i < bodyNew.length; i++) {
                    bodyOld[i].style.backgroundImage = bodyNew[i].style.backgroundImage
                }
            }

            setTimeout(refr, delay);
        }
    };

    xhr.open('GET', window.location.href, true);
    xhr.send();
}

var toUpdate = document.getElementsByClassName("online-update");
if (toUpdate.length > 0)
    setTimeout(refr, delay);


function toggle(sensor) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', "/sensor/toggle", true);
    xhr.send(sensor);

    setTimeout(refr, 10);
}