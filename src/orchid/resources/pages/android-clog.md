---
title: Android Clog
---

<section class="bs-docs-section">
  <h1 id="overview" class="page-header">Using Clog with Android</h1>
  
  <blockquote>
    Clog is a drop-in replacement of the standard Android Log. Featuing multiple logging profiles and the powerful Parselmouth formatting language, you'll wonder how you ever got by without it.
  </blockquote>

  <h3 id="features">Features</h3>

  <ul>
    <li>All features of the base Clog library</li>
    <li>Android-specific logging profiles for both development and production</li>
  </ul>

</section>

<section class="bs-docs-section">
  <h1 id="download" class="page-header">Get It</h1>

  <h3 id="download-include">Include In Your Project</h3>
  <p>
    Android Clog can be added to your project from Jitpack through Gradle.
  </p>

  <p>
    Add this to your project-level build.gradle:
  </p>
  <pre>
allprojects {
  repositories {
    ...
    maven { url "https://jitpack.io" }
  }
}</pre>

  <p>
    Add this to your module's dependencies:
  </p>
  <pre>
dependencies {
  ...
  compile('com.github.cjbrooks12:Android-Clog:v{{site.version}}@aar') {
    transitive = true;
  }
}</pre>

  <h3 id="download-github">Github</h3>
  <p>
    Full source can be viewed on Github. Feel free to send me a PR.
  </p>
  <div>
    <a href="https://github.com/cjbrooks12/Android-Clog">Github Repo</a>
  </div>

  <h3 id="download-jitpack">Jitpack</h3>
  <p>
    Download the most recent commits, or see instructions for installing with Maven, at Jitpack.
  </p>
  <div>
    <a href="https://jitpack.io/#cjbrooks12/Android-Clog"><img src="https://camo.githubusercontent.com/86295ad9ade648308e2328e7b2aba032fed9cc0c/68747470733a2f2f6a69747061636b2e696f2f762f636a62726f6f6b7331322f416e64726f69642d436c6f672e737667" alt="" data-canonical-src="https://jitpack.io/v/cjbrooks12/Android-Clog.svg" style="max-width:100%;"></a>
  </div>

  <h3 id="download-javadoc">View Javadoc</h3>
  <p>
    View javadoc, hosted by Jitpack.
  </p>
  <div>
    <a href="https://jitpack.io/com/github/cjbrooks12/Clog/v{{site.version}}/javadoc/"><img src="https://camo.githubusercontent.com/e987e48872f10d633fc044d7e20e7b82f99df591/68747470733a2f2f696d672e736869656c64732e696f2f6769746875622f7461672f636a62726f6f6b7331322f436c6f672e7376673f6d61784167653d32353932303030266c6162656c3d6a617661646f63" alt="JitPack Javadoc" data-canonical-src="https://img.shields.io/github/tag/cjbrooks12/Clog.svg?maxAge=2592000&amp;label=javadoc" style="max-width:100%;"></a>
  </div>
</section>

<section class="bs-docs-section">
  <h1 id="use" class="page-header">Use in your apps</h1>

  <h3 id="use-implementation">Clog implementation</h3>

  <p>See <a href="https://github.com/cjbrooks12/Clog">https://github.com/cjbrooks12/Clog</a> for information on Clog.</p>

  <p>Android Clog provides simple Android Log implementations for Clog. The mapping between Clog levels and Android Clog levels is one-to-one, as the Clog API was designed to directly replace Log:</p>

  <p><code>Clog.d(...) --&gt; Log.d(...)</code></p>

  <p><code>Clog.e(...) --&gt; Log.e(...)</code></p>

  <p><code>Clog.i(...) --&gt; Log.i(...)</code></p>

  <p><code>Clog.v(...) --&gt; Log.v(...)</code></p>

  <p><code>Clog.w(...) --&gt; Log.w(...)</code></p>

  <p><code>Clog.wtf(...) --&gt; Log.wtf(...)</code></p>

  <p>In addition, Clog tags work exactly like Android Log tags, like so:</p>

  <pre>
d(String tag, String message) {
  Log.d(tag, message);
}</pre>

  <p>Getting Android Clog integrated into your project is easy. Add the following to your Activity's <code>onCreate()</code>:</p>

  <pre>
if(BuildConfig.DEBUG) {
  Clog.setCurrentProfile("dev", AndroidClog.getDevelopmentClog());
}
else {
  Clog.setCurrentProfile("prod", AndroidClog.getProductionClog());
}</pre>

  <p>In development, all logs will be directed to the standard Log implementations shown above, but in production, all logs will be discarded. You can replace the production log profile with implementations that write to file or send to Crashlytics if you need that instead.</p>


</section>