<template id="app-layout">
  <div class="app-layout">
    <div class="main">
      <!-- Start of navbar -->
      <nav class="navbar">
        <a v-bind:class="activeIndex==0?'active a':'a'" @click="setActiveIndex(0)" href="/">Home</a>
        <a v-bind:class="activeIndex==1?'active a':'a'" @click="setActiveIndex(1)" href="/users">Users</a>
        <a v-bind:class="activeIndex==2?'active a':'a'" @click="setActiveIndex(2)" href="/activities">Activities</a>
        <a v-bind:class="activeIndex==3?'active a':'a'" @click="setActiveIndex(3)" href="/analytics">User Analytics</a>
        <a v-bind:class="activeIndex==4?'active a':'a'" @click="setActiveIndex(4)" href="/table">User Analytics Table</a>
      </nav>
      <!--End of nav bar-->
      <!--Start of main content area-->
      <slot></slot>
      <!--End of main content area-->
    </div>
  </div>
</template>

<script>
Vue.component("app-layout", {
  template: "#app-layout",
  data: () => ({
    activeIndex: sessionStorage.getItem("activeIndex") || 0
  }),
  methods: {
    setActiveIndex(index) {
      sessionStorage.setItem("activeIndex", index);
    },
  },
});
</script>

<style>
  .main{
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
  }
  .navbar{
    width: 75%;
    margin: 25px;
    padding: 10px 15px;
    background: rgba(255, 255, 255, 0.625);
    border-radius: 12px;
    box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
    backdrop-filter: blur(5px);
    -webkit-backdrop-filter: blur(5px);
    border: 1px solid rgba(255, 255, 255, 0.3);
    transition: all 0.3s cubic-bezier(0.165, 0.84, 0.44, 1);
  }
  .navbar:hover{
    box-shadow: 0 4px 30px rgba(0, 0, 0, 0.2);
    transform: translateY(-3px);
    scale: 1.01;
  }
  .a{
    height: 100%;
    color: #000;
    text-decoration: none;
    font-size: 20px;
    font-weight: 600;
    margin: 0 10px;
    transition: all 0.3s ease;
  }
  .active{
    color: rgb(255, 255, 255);
    background-color: #007bff;
    padding: 5px 20px;
    font-size: 20px;
    box-shadow: 0 4px 30px rgba(0, 0, 0, 0.1);
    border-radius: 6px;
    font-weight: 600;
    margin: 0 10px;
    transition: all 0.3s ease;
  }
  .active:hover{
    box-shadow: 0 4px 30px rgba(0, 0, 0, 0.2);
    scale: 1.05;
    color: white !important;
  }
  .a:hover{
    scale: 1.05;
    color: #007bff;
    text-decoration: none;
  }
</style>
