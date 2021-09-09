<template>
  <ion-page>
    <ion-header :translucent="true">
      <ion-toolbar>
        <ion-title>Blank</ion-title>
      </ion-toolbar>
    </ion-header>

    <ion-content :fullscreen="true">
      <ion-header collapse="condense">
        <ion-toolbar>
          <ion-title size="large">Blank</ion-title>
        </ion-toolbar>
      </ion-header>

      <div id="container">
        <ion-toolbar>
          <ion-text color="dark">
            <h6>results: {{ userData }}</h6>
          </ion-text>
        </ion-toolbar>
        <strong>Ready to create an app?</strong>
        <p>
          Start with Ionic
          <a
            target="_blank"
            rel="noopener noreferrer"
            href="https://ionicframework.com/docs/components"
            >UI Components</a
          >
        </p>
        <ion-button color="secondary" @click="login()"> Login with facebook </ion-button>
        <ion-button color="secondary" @click="$router.push({ name: 'About' })">
          go to about
        </ion-button>
      </div>
    </ion-content>
  </ion-page>
</template>

<script>
import {
  IonContent,
  IonHeader,
  IonPage,
  IonTitle,
  IonToolbar,
  IonButton,
  IonText,
} from "@ionic/vue";
// import { Plugins } from "@capacitor/core";
import { FacebookLogin } from "@capacitor-community/facebook-login";
import { ref } from "@vue/reactivity";
import { onBeforeMount } from "@vue/runtime-core";

export default {
  name: "Home",
  components: {
    IonContent,
    IonHeader,
    IonPage,
    IonTitle,
    IonToolbar,
    IonButton,
    IonText,
  },

  setup() {
    const resultToken = ref("");
    const userData = ref("");

    async function login() {
      const FACEBOOK_PERMISSIONS = [
        "email",
        "user_birthday",
        "user_photos",
        "user_gender",
      ];
      const result = await FacebookLogin.login({ permissions: FACEBOOK_PERMISSIONS });

      if (result.accessToken) {
        // Login successful.
        alert("result: " + result);
        console.log(`Facebook access token is ${result.accessToken.token}`);

        await getPofiles();
      }
    }

    async function getCurrentAccessToken() {
      const result = await FacebookLogin.getCurrentAccessToken();

      if (result.accessToken) {
        console.log(`Facebook access token is ${result.accessToken.token}`);
      }
    }

    async function getPofiles() {
      const result = await FacebookLogin.getProfile({ fields: ["email"] });
      alert(result.email);
      console.log(`Facebook user's email is ${result.email}`);
    }

    onBeforeMount(async () => {
      await getCurrentAccessToken();
    });

    return {
      login,
      resultToken,
      userData,
    };
  },
};
</script>

<style scoped>
#container {
  text-align: center;

  position: absolute;
  left: 0;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
}

#container strong {
  font-size: 20px;
  line-height: 26px;
}

#container p {
  font-size: 16px;
  line-height: 22px;

  color: #8c8c8c;

  margin: 0;
}

#container a {
  text-decoration: none;
}
</style>
