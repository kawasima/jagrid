var jagrid = (function () {
    var exports = {};
    var forEach = Array.prototype.forEach;
    var CELL_SIZE = 20;

    function camelize(s) {
        return s.replace(/(?:[-_])(\w)/g, function (_, c) {
            return c ? c.toUpperCase() : '';
        });
    }

    function wrapAll(targets, html) {
        if (targets.length == 0) return;
        var wrap = targets[0].ownerDocument.createElement(html);

        if (targets[0].parentNode) {
            targets[0].parentNode.insertBefore(wrap, targets[0]);
            var classNames = targets[0].parentNode.getAttribute("class");
            if (classNames) {
                targets[0].parentNode.removeAttribute("class");
                wrap.setAttribute("class", classNames);
            }
        }
        var arr = new Array(targets.length - 1);
        for (var i = 0, l = targets.length >>> 0; ++i !== l; arr[i] = targets[i]);
        forEach.call(arr, function (n) {
            wrap.appendChild(n);
        });
    }

    function makeGridImage() {
        var gridSheet = document.createElement("canvas");
        gridSheet.setAttribute("width", CELL_SIZE + "px");
        gridSheet.setAttribute("height", CELL_SIZE + "px");

        var context = gridSheet.getContext("2d");
        context.moveTo(CELL_SIZE, 0);
        context.lineTo(CELL_SIZE, CELL_SIZE);
        context.moveTo(0, CELL_SIZE);
        context.lineTo(CELL_SIZE, CELL_SIZE);

        context.strokeStyle = "#dadcdd";
        context.stroke();
        return gridSheet.toDataURL("image/png")
    }

    function toExcelNotation(n) {
        var s = "";
        while (n > 0) {
            s = String.fromCharCode((n - 1) % 26 + 65) + s;
            n = Math.floor((n - 1) / 26)
        }
        return s;
    }

    function columnHeader(sheet) {
        var headerTable = document.createElement("table");
        var headerRow = document.createElement("tr");
        headerTable.setAttribute("class", "jg-header column");
        for (var i = 1; i <= 256; i++) {
            var cell = document.createElement("td");
            cell.textContent = toExcelNotation(i);
            headerRow.appendChild(cell);
        }
        headerTable.appendChild(headerRow);
        sheet.insertBefore(headerTable, sheet.firstChild);
    }

    function rowHeader(sheet) {
        var headerTable = document.createElement("table");
        headerTable.setAttribute("class", "jg-header jg-row");
        for (var i = 0; i <= 256; i++) {
            var headerRow = document.createElement("tr");
            var cell = document.createElement("td");
            cell.textContent = i>0 ? i : "◢";
            headerRow.appendChild(cell);
            (headerTable.querySelector("tbody") || headerTable).appendChild(headerRow);
        }
        sheet.insertBefore(headerTable, sheet.firstChild);
    }


    function toGridTable(tbl) {
        var tableWidth = 0;
        var rowspans = [];
        forEach.call(tbl.querySelectorAll("tr"), function (row) {
            var rowWidth = 0;
            forEach.call(row.querySelectorAll("td,th"), function (cell) {
                rowspans.push(cell.getAttribute("rowspan") || 1);
                rowWidth += parseInt(cell.getAttribute("data-width") || 0);
            });
            tableWidth = Math.max(tableWidth, rowWidth);
        });

        var widthAry = new Array(tableWidth);
        var rowspanAry = new Array(tableWidth);
        forEach.call(tbl.querySelectorAll("tr"), function (row) {
            var columnIndex = 0;
            forEach.call(row.querySelectorAll("td,th"), function (cell) {
                while (rowspanAry[columnIndex] > 1 && columnIndex < tableWidth) {
                    rowspanAry[columnIndex++]--;
                }
                var rowspan = parseInt(cell.getAttribute("rowspan") || 1);
                var w = parseInt(cell.getAttribute("data-width"));
                var h = parseInt(cell.getAttribute("data-height"));

                if (isNaN(w)) w = widthAry[columnIndex];
                for (var i = 0; i < w; i++) {
                    rowspanAry[columnIndex + i] = rowspan;
                    widthAry[columnIndex + i] = w;
                }
                columnIndex += w;

                cell.removeAttribute("data-width");
                cell.setAttribute("colspan", w);
                if (!isNaN(h)) cell.style.height = CELL_SIZE * h + "px";
                wrapAll(cell.childNodes, "div");
            });
        });
        var spacerRow = document.createElement("tr");
        for (var i = 0; i < tableWidth; i++) {
            spacerRow.appendChild(document.createElement("td"));
        }
        var parent = tbl.querySelector("tbody") || tbl;
        parent.insertBefore(spacerRow, parent.firstChild);
    }

    var gridImage = makeGridImage();

    function _render(sheet) {
        sheet.style.backgroundImage = "url(" + gridImage + ")";
        forEach.call(sheet.childNodes, function (child) {
            if (child.nodeType != Node.ELEMENT_NODE) return;
            var x = parseInt(child.getAttribute("data-x"));
            var y = parseInt(child.getAttribute("data-y"));
            if (!isNaN(x) && !isNaN(y)) {
                child.style.position = "absolute";
                child.style.marginLeft = (CELL_SIZE * x) + "px";
                child.style.marginTop = (CELL_SIZE * y) + "px";
            }
        });
        rowHeader(sheet);
        columnHeader(sheet);
        forEach.call(sheet.querySelectorAll("table:not(.jg-header)"), function (table) {
            toGridTable(table);
        });
        forEach.call(sheet.querySelectorAll("[data-width]"), function (el) {
            var w = parseInt(el.getAttribute("data-width"));
            el.style.width = (CELL_SIZE * w) + "px";
            el.removeAttribute("data-width");
        });
        forEach.call(["margin-left", "margin-top", "margin-bottom"],

                     function (marginPosition) {
                         forEach.call(sheet.querySelectorAll("*[data-" + marginPosition + "]"),

                                      function (el) {
                                          var margin = parseInt(el.getAttribute("data-" + marginPosition) || 0);
                                          var current = parseInt((getComputedStyle(el))[marginPosition].replace(/px$/, ''));
                                          el.style[camelize(marginPosition)] = ((CELL_SIZE * margin) + current) + "px";
                                      });
                     });
        setupCellSelection(sheet);
    }

    function setupCellSelection(sheet) {
        var cursor = document.createElement("div");
        cursor.className = "jg-cell-cursor";
        cursor.style.display = "none";
        cursor.style.width = CELL_SIZE + "px";
        cursor.style.height = CELL_SIZE + "px";
        sheet.appendChild(cursor);

        sheet.addEventListener("click", function (e) {
            var rect = sheet.getBoundingClientRect();
            var scrollLeft = sheet.scrollLeft;
            var scrollTop = sheet.scrollTop;
            var x = e.clientX - rect.left + scrollLeft;
            var y = e.clientY - rect.top + scrollTop;

            var col = Math.floor(x / CELL_SIZE);
            var row = Math.floor(y / CELL_SIZE);

            // Column 0 is the row header area, row 0 is the column header area
            if (col < 1 || row < 1) return;

            cursor.style.display = "block";
            cursor.style.left = (col * CELL_SIZE) + "px";
            cursor.style.top = (row * CELL_SIZE) + "px";

            // Highlight column header
            var colHeader = sheet.querySelector("table.jg-header.column");
            if (colHeader) {
                forEach.call(colHeader.querySelectorAll("td.jg-active, th.jg-active"), function (el) {
                    el.classList.remove("jg-active");
                });
                var colCells = colHeader.querySelectorAll("td, th");
                // col is 1-based in the grid (col 0 is row header), header cells are 0-indexed
                if (colCells[col - 1]) {
                    colCells[col - 1].classList.add("jg-active");
                }
            }

            // Highlight row header
            var rowHeader = sheet.querySelector("table.jg-header.jg-row");
            if (rowHeader) {
                forEach.call(rowHeader.querySelectorAll("td.jg-active, th.jg-active"), function (el) {
                    el.classList.remove("jg-active");
                });
                var rowRows = rowHeader.querySelectorAll("tr");
                // row is 1-based in the grid, row header rows are 0-indexed (first row is "◢")
                if (rowRows[row]) {
                    var cell = rowRows[row].querySelector("td, th");
                    if (cell) cell.classList.add("jg-active");
                }
            }
        });
    }

    exports.render = function (dom) {
        if (dom) _render(dom);
        else forEach.call(document.querySelectorAll(".jagrid"), _render);
    };

    return exports;
})();

if(document.addEventListener) {
  document.addEventListener('DOMContentLoaded',
                            function(e) { jagrid.render(); }, false);
} else if (window.addEventListener) {
  window.addEventListener('load',
                          function(e) { jagrid.render(); }, false);
}
