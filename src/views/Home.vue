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
import { FacebookLogin } from "@rdlabo/capacitor-facebook-login";
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

    const getCurrentState = async () => {
      const result = await FacebookLogin.getCurrentAccessToken();
      try {
        console.log(result);
        if (result && result.accessToken) {
          await getUserInfo();
          return (resultToken.value = result);
        }
      } catch (e) {
        console.log(e);
      }
    };

    onBeforeMount(async () => {
      await getCurrentState();
    });

    const getUserInfo = async (event) => {
      const response = await fetch(
        `https://graph.facebook.com/${event.accessToken.userId}?fields=id,name,gender,link,picture&type=large&access_token=${event.accessToken.token}`
      );
      // const myJson = await response.json();
      console.log("my data: ", await response.json());
    };

    const login = async () => {
      const FACEBOOK_PERMISSIONS = ["public_profile", "email"];

      const result = await FacebookLogin.login({
        permissions: FACEBOOK_PERMISSIONS,
      });
      if (result && result.accessToken) {
        await getUserInfo(result);
        return (userData.value = result);
      }
    };

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
