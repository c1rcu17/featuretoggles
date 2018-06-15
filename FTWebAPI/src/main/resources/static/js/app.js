Vue.use(VueResource);
Vue.use(Buefy.default)

function headers(token) {
    return {
        'Authorization': token
    }
}

new Vue({
    el: '#app',
    template: '#app-template',
    data: function () {
        return {
            username: 'admin',
            password: '',
            token: '',
            toggles: [],
            mqttLogInterval: null,
            mqttLog: '',
            colors: [
                'is-primary',
                'is-info',
                'is-success',
                'is-warning',
                'is-danger'
            ]
        }
    },
    watch: {
        token: function () {
            var self = this;

            self.toggles = [];
            self.$http
                .get('/api/toggles', {
                    headers: headers(self.token)
                })
                .then(function (response) {
                    response.body.forEach(function (t) {
                        self.toggles.push(Object.assign({
                            color: self.colors[Math.floor(
                                Math.random() * Math.floor(self.colors.length))]
                        }, t));
                    });
                })
                .catch(function (err) {
                });

            self.mqttLogInterval = setInterval(function () {
                self.$http.get('/api/mqtt/log', {
                    headers: headers(self.token),
                    responseType: 'text'
                })
                    .then(function (response) {
                        if (response && response.bodyText) {
                            self.mqttLog = response.bodyText.split('\n').reverse().join('\n') + self.mqttLog;
                        }
                    })
                    .catch(function () {
                        self.abort();
                    });
            }, 1000);
        }
    },
    methods: {
        getToken: function () {
            var self = this;
            var password = self.password;
            self.password = '';
            self.$http
                .post('/api/auth', {
                    username: self.username,
                    password: password
                })
                .then(function (response) {
                    self.token = response.body.token;
                })
                .catch(function (err) {
                });
        },
        toggleChanged: function (toggle) {
            var self = this;
            self.$http
                .patch('/api/toggles/' + toggle.name, {
                    enabled: toggle.enabled
                }, {
                    headers: headers(self.token)
                })
                .then(function () {
                    return self.$http.get('/api/toggles/' + toggle.name, {
                        headers: headers(self.token)
                    });
                })
                .then(function (response) {
                    Object.assign(toggle, response.body);
                })
                .catch(function () {
                    self.abort();
                });
        },
        abort: function () {
            var self = this;
            if (self.mqttLogInterval) {
                clearInterval(self.mqttLogInterval);
            }

            self.token = '';
            self.toggles = [];
        }
    }
});
