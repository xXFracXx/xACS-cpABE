/**
 * File attachment wrapper.
 *
 * @author Htmlstream
 * @version 1.0
 *
 */
;
(function ($) {
    'use strict';

    $.HSCore.components.HSFileAttachment = {
        /**
         *
         *
         * @var Object _baseConfig
         */
        _baseConfig: {
            changeInput: '<div class="g-parent g-pos-rel g-height-230 g-bg-gray-light-v8--hover g-brd-around g-brd-style-dashed g-brd-gray-light-v7 g-brd-lightblue-v3--hover g-rounded-4 g-transition-0_2 g-transition--ease-in g-pa-15 g-pa-30--md g-mb-0">\
              <div class="d-md-flex align-items-center g-absolute-centered--md w-100 g-width-auto--md">\
                <div>\
                  <div class="g-pos-rel g-width-80 g-width-100--lg g-height-80 g-height-100--lg g-bg-gray-light-v8 g-bg-white--parent-hover rounded-circle g-mb-20 g-mb-0--md g-transition-0_2 g-transition--ease-in mx-auto mx-0--md">\
                    <i class="hs-admin-cloud-up g-absolute-centered g-font-size-30 g-font-size-36--lg g-color-lightblue-v3"></i>\
                  </div>\
                </div>\
                <div class="text-center text-md-left g-ml-20--md">\
                  <h3 class="g-font-weight-400 g-font-size-16 g-color-black g-mb-10">Upload Your Files</h3>\
                  <p class="g-font-weight-300 g-color-gray-dark-v6 mb-0">Drag your files here or click upload button and browse from your computer.</p>\
                </div>\
              </div>\
            </div>',
            uploadFile: {
                url: 'Upload',
                data: {},
                type: 'POST',
                enctype: 'multipart/form-data',
                beforeSend: function () {}
            }
        },

        /**
         *
         *
         * @var jQuery pageCollection
         */
        pageCollection: $(),

        /**
         * Initialization of File attachment wrapper.
         *
         * @param String selector (optional)
         * @param Object config (optional)
         *
         * @return jQuery pageCollection - collection of initialized items.
         */

        init: function (selector, config) {
            if (!selector)
                return;

            var $collection = $(selector);

            if (!$collection.length)
                return;

            config = config && $.isPlainObject(config) ? $.extend(true, {}, this._baseConfig, config) : this._baseConfig;

            this.initFileAttachment(selector, config);
        },

        initFileAttachment: function (el, conf) {
            //Variables
            var $el = $(el);

            //Actions
            $el.each(function () {
                var $this = $(this);

                $this.filer($.extend(true, {}, conf, {
                    dragDrop: {}
                }));
            });
        }
    };
})(jQuery);
