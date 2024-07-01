module.exports = function(config) {
    config.set({
      frameworks: ['jasmine', '@angular-devkit/build-angular'],
      plugins: [
        require('karma-jasmine'),
        require('karma-chrome-launcher'),
        require('@angular-devkit/build-angular/plugins/karma')
      ],
      browsers: ['Chrome'],
      files: [
        'src/**/*.spec.ts'  // Caminho para os arquivos de teste
      ],
      preprocessors: {
        'src/**/*.spec.ts': ['@angular-devkit/build-angular']  // Pré-processamento para arquivos de teste
      },
      reporters: ['progress', 'kjhtml'],
      port: 9876,
      colors: true,
      logLevel: config.LOG_INFO,
      autoWatch: true,
      singleRun: false,
      restartOnFileChange: true
    });
  };