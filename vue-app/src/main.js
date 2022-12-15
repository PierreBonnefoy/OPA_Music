import { createApp, VueElement } from "vue";
import App from "./App.vue";
import router from "./router";

VueElement.prototype.$logged = true

const app = createApp(App);

app.use(router);

app.mount("#app");

