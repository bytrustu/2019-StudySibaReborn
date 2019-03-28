(function () {
    this.next = ar => ({ next: ar });
    this.error = ar => ({ error: ar });
    this.liar = function (cb, value) {
        if (cb instanceof Function) {
            return new Promise(function (resolve, reject) {
                let pt = { next: resolve, error: reject };
                if (value !== undefined) { pt.value = value; }
                cb(pt);
            });
        } else {
            let cn;
            cb.forEach(cbi => {
                let key = Object.keys(cbi)[0];
            let val = cbi[key];
            if (!cn) {
                cn = liar(val);
            } else {
                cn = cn[key](val);
            }
        });
        }
    };
    [{ next: 'then' }, { error: 'catch' }].forEach(mn => {
        let key = Object.keys(mn)[0];
    if (!Promise.prototype[key]) {
        let val = mn[key];
        Promise.prototype[key] = function (fn) {
            return this[val](value => liar(fn, value));
        };
    }
});
}).call(this);