/**
 *  AirPollution and Weather Info
 *
 *  Copyright 2018 GayoungKoh
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */

metadata {
	definition (name: "AirPollution and Weather Info", namespace: "dianakoh", author: "GayoungKoh") {
		capability "Air Quality Sensor"
		capability "Carbon Monoxide Detector" // co
		capability "Dust Sensor" // For PM2.5
        capability "Illuminance Measurement"
        capability "Temperature Measurement"
        capability "Relative Humidity Measurement"
		capability "Ultraviolet Index"
		capability "Polling"
        capability "Configuration"
		capability "Refresh"
		capability "Sensor"
        capability "Image Capture"

		// Air Korea infos for WebCore
		attribute "pm25_value", "number"
        attribute "pm10_value", "number"
        attribute "o3_value", "number"
		attribute "no2_value", "number"
		attribute "so2_value", "number"
        attribute "co_value", "number"
        attribute "khai_value", "number"
        attribute "airQualityStatus", "string"
        attribute "maskIcon", "string"
        
        // Weather Station infos
        attribute "localSunrise", "string"
		attribute "localSunset", "string"
        attribute "city", "string"
		attribute "timeZoneOffset", "string"
		attribute "weather", "string"
		attribute "wind", "string"
		attribute "weatherIcon", "string"
		attribute "forecastIcon", "string"
		attribute "feelsLike", "string"
		attribute "percentPrecip", "string"
        attribute "clothes", "string"

        command "refresh"
        command "pollAirKorea"
        command "pollWunderground"
	}

	simulator {
		// TODO: define status and reply messages here
	}

	tiles (scale: 2){
    
        multiAttributeTile(name:"feelsLikeClothes", type: "generic", width:6, height: 4) {
        	tileAttribute("device.feelsLikeIcon", key: "PRIMARY_CONTROL") {
            	attributeState "매우추움", label: '${name}', icon:"http://cs.sookmyung.ac.kr/~uslab/gy/image/winterClothes2.png", backgroundColor: "#153591" //icon: Created by Oleksndr Panasovskyi from Noun Project & Created by Olga from Noun Project
            	attributeState "추움", label: '${name}', icon:"http://cs.sookmyung.ac.kr/~uslab/gy/image/coatAndSweater.png", backgroundColor: "#1E9CBB" //icon: Created by www.mindgraphy.com from Noun Project & Created by VectorBakery from Noun Project
                attributeState "약간추움", label:'${name}', icon:"http://cs.sookmyung.ac.kr/~uslab/gy/image/trenchAndJacket2.png", backgroundColor:"#90D2A7" //icon: Created by Iconic from Noun Project & Created by Oleksndr Panasovskyi from Noun Project
                attributeState "보통", label: '${name}', icon:"http://cs.sookmyung.ac.kr/~uslab/gy/image/hoodedJacketAndLeatherJacket3.png", backgroundColor:"#44B621" //icon: Created by Oleksndr Panasovskyi from Noun Project
                attributeState "약간더움", label: '${name}', icon:"http://cs.sookmyung.ac.kr/~uslab/gy/image/cardigan2.png", backgroundColor:"#f1D801" //icon: Created by Artem  Kovyazin from Noun Project & Created by Oleksndr Panasovskyi from Noun Project
                attributeState "더움", label: '${name}', icon:"http://cs.sookmyung.ac.kr/~uslab/gy/image/longSleeveShirtsAndPants.png", backgroundColor:"#d04E00" //icon: Created by Oleksndr Panasovskyi from Noun Project
                attributeState "매우더움", label: '${name}', icon:"http://cs.sookmyung.ac.kr/~uslab/gy/image/shortSleeveShirtAndCap.png", backgroundColor:"#BC2323" //icon: Created by suib icon from Noun Project & Created by Oleksndr Panasovskyi from Noun Project
                attributeState "매우매우더움", label: '${name}', icon:"http://cs.sookmyung.ac.kr/~uslab/gy/image/sleevelessShirtAndSandal.png", backgroundColor:"#800000" //icon: Created by suib icon from Noun Project & Created by Oleksndr Panasovskyi from Noun Project
                
            }
            
            tileAttribute("device.clothes", key: "SECONDARY_CONTROL") {
            	attributeState("default", label: '${currentValue}')
            }
        
        }

        valueTile("airquality_infos", "", width: 3, height: 1, decoration: "flat") {
            state "default", label:' 대기 오염 정보 '
        }
        
        valueTile("airQuality_Label", "", width: 1, height: 1, decoration: "flat") {
        	state "default", label: '대기질\n수치'
        }
        valueTile("airQuality", "device.airQuality", width: 1, height: 1) {
        	state "default", label:'${currentValue}', backgroundColors: [
            	[value: -1, color: "#000000"],
                [value: 0, color: "#25AEFB"],
                [value: 50, color: "#92F101"],
            	[value: 100, color: "#FDE905"],
            	[value: 250, color: "#FC3037"]
            ]
        }
        
         standardTile("maskIcon", "device.maskIcon", decoration: "flat") {
        	state "매우나쁨", icon: "http://cs.sookmyung.ac.kr/~uslab/gy/image/maskRed.png", label: ""   //icon: Created by Arafat Uddin from Noun Project
            state "나쁨", icon: "http://cs.sookmyung.ac.kr/~uslab/gy/image/maskYellow.png", label: ""      //icon: Created by Arafat Uddin from Noun Project
            state "보통", icon: "http://cs.sookmyung.ac.kr/~uslab/gy/image/maskGreen.png", label: ""     //icon: Created by Arafat Uddin from Noun Project
            state "좋음", icon: "http://cs.sookmyung.ac.kr/~uslab/gy/image/smile.png", label: ""       //icon: Created by Barracuda from Noun Project
            
        }

        valueTile("pm10_label", "", decoration: "flat") {
            state "default", label:'PM10\nμg/m'
        }

        valueTile("pm10_value", "device.pm10_value", decoration: "flat") {
        	state "default", label:'${currentValue}', unit:"μg/m³", backgroundColors:[
				[value: -1, color: "#000000"],
            	[value: 0, color: "#25AEFB"],
            	[value: 30, color: "#92F101"],
            	[value: 80, color: "#FDE905"],
            	[value: 150, color: "#FC3037"]
            ]
        }

        valueTile("pm25_label", "", decoration: "flat") {
            state "default", label:'PM2.5\nμg/m'
        }

		valueTile("pm25_value", "device.fineDustLevel", decoration: "flat") {
        	state "default", label:'${currentValue}', unit:"μg/m³", backgroundColors:[
				[value: -1, color: "#000000"],
            	[value: 0, color: "#25aefb"],
            	[value: 15, color: "#92f101"],
            	[value: 35, color: "#fde905"],
            	[value: 75, color: "#fc3037"]
            ]
        }

        valueTile("pm25_grade", "device.dustLevel", decoration: "flat") {
            state "default", label:'${currentValue}'
        }

        valueTile("o3_label", "", decoration: "flat") {
            state "default", label:'오존\nppm'
        }

        valueTile("o3_value", "device.o3_value", decoration: "flat") {
            state "default", label: '${currentValue}', unit: "ppm",
            backgroundColors:[
				[value: -1, color: "#000000"],
            	[value: 0.001, color: "#25aefb"],
            	[value: 0.03, color: "#92f101"],
            	[value: 0.09, color: "#fde905"],
            	[value: 0.15, color: "#fc3037"]
            ]
        }

        valueTile("no2_label", "", decoration: "flat") {
            state "default", label:'이산화질소\nppm'
        }

        valueTile("no2_value", "device.no2_value", decoration: "flat") {
            state "default", label:'${currentValue}', unit: "ppm",
            backgroundColors:[
				[value: -1, color: "#000000"],
            	[value: 0, color: "#25aefb"],
            	[value: 0.03, color: "#92f101"],
            	[value: 0.06, color: "#fde905"],
            	[value: 0.2, color: "#fc3037"]
            ]
        }

        valueTile("so2_label", "", decoration: "flat") {
            state "default", label:'아황산가스\nppm'
        }

        valueTile("so2_value", "device.so2_value", decoration: "flat") {
            state "default", label:'${currentValue}', unit: "ppm",
            backgroundColors:[
				[value: -1, color: "#000000"],
            	[value: 0, color: "#25aefb"],
            	[value: 0.02, color: "#92f101"],
            	[value: 0.05, color: "#fde905"],
            	[value: 0.15, color: "#fc3037"]
            ]
        }

        valueTile("co_label", "", decoration: "flat") {
            state "default", label:'일산화탄소\nppm'
        }

        valueTile("co_value", "device.co_value", decoration: "flat") {
            state "default", label:'${currentValue}', unit: "ppm",
            backgroundColors:[
				[value: -1, color: "#000000"],
            	[value: 0, color: "#25aefb"],
            	[value: 2, color: "#92f101"],
            	[value: 9, color: "#fde905"],
            	[value: 15, color: "#fc3037"]
            ]
        }

        valueTile("wunderground_infos", "", width: 3, height: 1, decoration: "flat") {
            state "default", label:' 날씨 정보 '
        }

        valueTile("temperature_label", "", decoration: "flat") {
            state "default", label:'온도'
        }

        valueTile("temperature_value", "device.temperature") {
			state "default", label:'${currentValue}°'
            
        }
				/*backgroundColors:[
					[value: 0, color: "#153591"],
					[value: 6, color: "#1e9cbb"],
					[value: 15, color: "#90d2a7"],
					[value: 23, color: "#44b621"],
					[value: 28, color: "#f1d801"],
					[value: 35, color: "#d04e00"],
                    [value: 36, color: "bc2323"]
				]
		}*/

        valueTile("humidity_label", "", decoration: "flat") {
            state "default", label:'습도'
        }

		valueTile("humidity_value", "device.humidity", decoration: "flat") {
			state "default", label:'${currentValue}%'
		}
        
        valueTile("weather_label", "", decoration: "flat") {
			state "default", label:'날씨'
		}

        standardTile("weatherIcon", "device.weatherIcon", decoration: "flat") {
            state "00", icon:"https://smartthings-twc-icons.s3.amazonaws.com/00.png", label: ""
            state "01", icon:"https://smartthings-twc-icons.s3.amazonaws.com/01.png", label: ""
            state "02", icon:"https://smartthings-twc-icons.s3.amazonaws.com/02.png", label: ""
            state "03", icon:"https://smartthings-twc-icons.s3.amazonaws.com/03.png", label: ""
            state "04", icon:"https://smartthings-twc-icons.s3.amazonaws.com/04.png", label: ""
            state "05", icon:"https://smartthings-twc-icons.s3.amazonaws.com/05.png", label: ""
            state "06", icon:"https://smartthings-twc-icons.s3.amazonaws.com/06.png", label: ""
            state "07", icon:"https://smartthings-twc-icons.s3.amazonaws.com/07.png", label: ""
            state "08", icon:"https://smartthings-twc-icons.s3.amazonaws.com/08.png", label: ""
            state "09", icon:"https://smartthings-twc-icons.s3.amazonaws.com/09.png", label: ""
            state "10", icon:"https://smartthings-twc-icons.s3.amazonaws.com/10.png", label: ""
            state "11", icon:"https://smartthings-twc-icons.s3.amazonaws.com/11.png", label: ""
            state "12", icon:"https://smartthings-twc-icons.s3.amazonaws.com/12.png", label: ""
            state "13", icon:"https://smartthings-twc-icons.s3.amazonaws.com/13.png", label: ""
            state "14", icon:"https://smartthings-twc-icons.s3.amazonaws.com/14.png", label: ""
            state "15", icon:"https://smartthings-twc-icons.s3.amazonaws.com/15.png", label: ""
            state "16", icon:"https://smartthings-twc-icons.s3.amazonaws.com/16.png", label: ""
            state "17", icon:"https://smartthings-twc-icons.s3.amazonaws.com/17.png", label: ""
            state "18", icon:"https://smartthings-twc-icons.s3.amazonaws.com/18.png", label: ""
            state "19", icon:"https://smartthings-twc-icons.s3.amazonaws.com/19.png", label: ""
            state "20", icon:"https://smartthings-twc-icons.s3.amazonaws.com/20.png", label: ""
            state "21", icon:"https://smartthings-twc-icons.s3.amazonaws.com/21.png", label: ""
            state "22", icon:"https://smartthings-twc-icons.s3.amazonaws.com/22.png", label: ""
            state "23", icon:"https://smartthings-twc-icons.s3.amazonaws.com/23.png", label: ""
            state "24", icon:"https://smartthings-twc-icons.s3.amazonaws.com/24.png", label: ""
            state "25", icon:"https://smartthings-twc-icons.s3.amazonaws.com/25.png", label: ""
            state "26", icon:"https://smartthings-twc-icons.s3.amazonaws.com/26.png", label: ""
            state "27", icon:"https://smartthings-twc-icons.s3.amazonaws.com/27.png", label: ""
            state "28", icon:"https://smartthings-twc-icons.s3.amazonaws.com/28.png", label: ""
            state "29", icon:"https://smartthings-twc-icons.s3.amazonaws.com/29.png", label: ""
            state "30", icon:"https://smartthings-twc-icons.s3.amazonaws.com/30.png", label: ""
            state "31", icon:"https://smartthings-twc-icons.s3.amazonaws.com/31.png", label: ""
            state "32", icon:"https://smartthings-twc-icons.s3.amazonaws.com/32.png", label: ""
            state "33", icon:"https://smartthings-twc-icons.s3.amazonaws.com/33.png", label: ""
            state "34", icon:"https://smartthings-twc-icons.s3.amazonaws.com/34.png", label: ""
            state "35", icon:"https://smartthings-twc-icons.s3.amazonaws.com/35.png", label: ""
            state "36", icon:"https://smartthings-twc-icons.s3.amazonaws.com/36.png", label: ""
            state "37", icon:"https://smartthings-twc-icons.s3.amazonaws.com/37.png", label: ""
            state "38", icon:"https://smartthings-twc-icons.s3.amazonaws.com/38.png", label: ""
            state "39", icon:"https://smartthings-twc-icons.s3.amazonaws.com/39.png", label: ""
            state "40", icon:"https://smartthings-twc-icons.s3.amazonaws.com/40.png", label: ""
            state "41", icon:"https://smartthings-twc-icons.s3.amazonaws.com/41.png", label: ""
            state "42", icon:"https://smartthings-twc-icons.s3.amazonaws.com/42.png", label: ""
            state "43", icon:"https://smartthings-twc-icons.s3.amazonaws.com/43.png", label: ""
            state "44", icon:"https://smartthings-twc-icons.s3.amazonaws.com/44.png", label: ""
            state "45", icon:"https://smartthings-twc-icons.s3.amazonaws.com/45.png", label: ""
            state "46", icon:"https://smartthings-twc-icons.s3.amazonaws.com/46.png", label: ""
            state "47", icon:"https://smartthings-twc-icons.s3.amazonaws.com/47.png", label: ""
            state "na", icon:"https://smartthings-twc-icons.s3.amazonaws.com/na.png", label: ""
        }
        
        valueTile("weather_value", "device.weather", width: 3, height: 1, decoration: "flat") {
			state "default", label:'${currentValue}'
            
		}

		valueTile("feelsLike_label", "", decoration: "flat") {
			state "default", label:'체감 온도'
		}

        valueTile("feelsLike_value", "device.feelsLike", decoration: "flat") {
			state "default", label:'${currentValue}°',
            backgroundColors:[
            	[value: 0, color: "#153591"],
            	[value: 6, color: "#1e9cbb"],
				[value: 10, color: "#90d2a7"],
				[value: 12, color: "#44b621"],
				[value: 17, color: "#f1d801"],
				[value: 20, color: "#d04e00"],
				[value: 23, color: "#bc2323"],
                [value: 27, color: "#800000"]
			]
		}

        valueTile("wind_label", "", decoration: "flat") {
			state "default", label:'바람세기\nmph'
		}

		valueTile("wind_value", "device.wind", decoration: "flat") {
			state "default", label:'${currentValue}'
		}

		valueTile("percentPrecip_label", "", decoration: "flat") {
			state "default", label:'강수확율'
		}

		valueTile("percentPrecip_value", "device.percentPrecip", decoration: "flat") {
			state "default", label:'${currentValue}%'
		}

		valueTile("ultravioletIndex_label", "", decoration: "flat") {
			state "default", label:'UV\n자외선'
		}

		valueTile("ultravioletIndex_value", "device.ultravioletIndex", decoration: "flat") {
			state "default", label:'${currentValue}'
		}
        
        valueTile("rise_label", "", width: 2, decoration: "flat") {
			state "default", label:'일출'
		}

		valueTile("rise_value", "device.localSunrise", width: 2, decoration: "flat") {
			state "default", label:'${currentValue}'
		}

		valueTile("set_label", "", width: 2, decoration: "flat") {
			state "default", label:'일몰'
		}

		valueTile("set_value", "device.localSunset", width: 2, decoration: "flat") {
			state "default", label:'${currentValue}'
		}

		valueTile("refresh_label", "", width: 1, decoration: "flat") {
			state "default", label:'refresh'
		}
        
        standardTile("refresh_value", "device.weather", width: 4,  height: 1, decoration: "flat") {
			state "default", label: "", action: "refresh", icon:"st.secondary.refresh"
		}
        
        htmlTile(name:"graphTile", action:"getGraphHTML", 
        	whitelist:["code.jquery.com", "ajax.googleapis.com", "https://thingspeak.com"
       ], width:6, height:4){}
       
        standardTile("image", "device.image", width: 1, height: 1, canChangeIcon: false, inactiveLabel: true, canChangeBackground: true) {
        state "default", label: "", action: "", icon: "st.camera.dropcam-centered", backgroundColor: "#FFFFFF"
    }

    carouselTile("cameraDetails", "device.image", width: 6, height: 4) { }

    standardTile("take", "device.image", width: 1, height: 1, canChangeIcon: false, inactiveLabel: true, canChangeBackground: false) {
        state "take", label: "Take", action: "Image Capture.take", icon: "st.camera.dropcam", backgroundColor: "#FFFFFF", nextState:"taking"
        state "taking", label:'Taking', action: "", icon: "st.camera.dropcam", backgroundColor: "#00A0DC"
        state "image", label: "Take", action: "Image Capture.take", icon: "st.camera.dropcam", backgroundColor: "#FFFFFF", nextState:"taking"
    }
       
       
		main (["feelsLikeClothes"])
		details(["feelsLikeClothes",
                "wunderground_infos", "weather_value", 
                "weather_label", "temperature_label", "feelsLike_label", "percentPrecip_label", "humidity_label", "ultravioletIndex_label", 
                "weatherIcon", "temperature_value", "feelsLike_value", "percentPrecip_value", "humidity_value", "ultravioletIndex_value", 
        		"airquality_infos", "airQuality_Label", "airQuality", "maskIcon",
                "pm10_label", "pm25_label", "o3_label", "no2_label", "so2_label", "co_label",
                "pm10_value", "pm25_value", "o3_value", "no2_value", "so2_value", "co_value",
                "graphTile", "cameraDetails", "take", "refresh_label", "refresh_value"
                //"cameraDetails", "take", "refresh_label", "refresh_value"
                ])}


	preferences {
        input "accessKey", "text", type: "password", title: "AirKorea API Key", description: "www.data.go.kr에서 apikey 발급 받으세요", required: true
		input "stationName", "text", title: "Station name(조회: 아래 링크)", description: "weekendproject.net:8081/api/airstation/지역명", required: true
        input "fakeStationName", "text", title: "Fake Station name(option)", description: "Tile에 보여질 이름 입력하세요", required: false
	}


}
mappings {
      path("/getGraphHTML") {action: [ GET: "getGraphHTML" ]}
}

// parse events into attributes
def parse(String description) {
	log.debug "Parsing '${description}'"
}

def installed() {
   runEvery30Minutes(refresh)
   //runIn(0, refresh)
}

def uninstalled() {
	unschedule()
    runEvery30Minutes(refresh)
  //runIn(0, refresh)
}

def updated() {
	unschedule()
   runEvery30Minutes(refresh)
   //runIn(0, refresh)
}

def refresh() {
	log.debug "refresh()"
	try {
        pollAirKorea()
    } catch (e) {
        log.error "error: pollAirKorea $e"
    }

	try {
        pollWunderground()
    } catch (e) {
        log.error "error: pollWunderground $e"
    }
}

def configure() {
	log.debug "Configuare()"
}

// Air Korea handle commands

def pollAirKorea() {
	log.debug "pollAirKorea()"
    if (stationName && accessKey) {
        def params = [
    	    uri: "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?stationName=${stationName}&dataTerm=DAILY&pageNo=1&numOfRows=1&ServiceKey=${accessKey}&ver=1.3&_returnType=json",
        	contentType: 'application/json'
    	]
        try {
    		log.debug "Data will repoll every ${60} minutes"
        	log.debug "uri: ${params.uri}"

            httpGet(params) {resp ->
                resp.headers.each {
                    log.debug "${it.name} : ${it.value}"
                }
                // get the contentType of the response
                log.debug "response contentType: ${resp.contentType}"
                // get the status code of the response
                log.debug "response status code: ${resp.status}"
                if (resp.status == 200) {
                    // get the data from the response body
                    //log.debug "response data: ${resp.data}"

                    if( resp.data.list[0].pm10Value != "-" ) {
                        log.debug "PM10 value: ${resp.data.list[0].pm10Value}"
                        state.pm = resp.data.list[0].pm10Value
                        uploadDataToThingSpeak2(state.pm)
                        sendEvent(name: "pm10_value", value: resp.data.list[0].pm10Value, unit: "μg/m³", isStateChange: true)
                    } else {
                    	sendEvent(name: "pm10_value", value: "--", unit: "μg/m³", isStateChange: true)
                    }

                    if( resp.data.list[0].pm25Value != "-" ) {
                        log.debug "PM25 value: ${resp.data.list[0].pm25Value}"
                        sendEvent(name: "pm25_value", value: resp.data.list[0].pm25Value, unit: "μg/m³", isStateChange: true)
                        sendEvent(name: "fineDustLevel", value: resp.data.list[0].pm25Value, unit: "μg/m³", isStateChange: true)
                    } else {
                    	sendEvent(name: "pm25_value", value: "--", unit: "μg/m³", isStateChange: true)
                    }

                    if( resp.data.list[0].pm25Grade != "-" ) {
                        log.debug "PM25 grade: ${resp.data.list[0].pm25Grade}"
                        sendEvent(name: "dustLevel", value: resp.data.list[0].pm25Grade, unit: "", isStateChange: true)
                    } else {
                    	sendEvent(name: "dustLevel", value: "--", unit: "μg/m³", isStateChange: true)
                    }

                    def display_value
                    if( resp.data.list[0].o3Value != "-" ) {
                    	log.debug "Ozone: ${resp.data.list[0].o3Value}"
                        display_value = "\n" + resp.data.list[0].o3Value + "\n"
                        sendEvent(name: "o3_value", value: display_value as String, unit: "ppm", isStateChange: true)
                    } else
                    	sendEvent(name: "o3_value", value: "--", unit: "ppm", isStateChange: true)

                    if( resp.data.list[0].no2Value != "-" ) {
                        log.debug "NO2: ${resp.data.list[0].no2Value}"
                        display_value = "\n" + resp.data.list[0].no2Value + "\n"
                        sendEvent(name: "no2_value", value: display_value as String, unit: "ppm", isStateChange: true)
                    } else
                    	sendEvent(name: "no2_value", value: "--", unit: "ppm", isStateChange: true)

                    if( resp.data.list[0].so2Value != "-" ) {
                        log.debug "SO2: ${resp.data.list[0].so2Value}"
                        display_value = "\n" + resp.data.list[0].so2Value + "\n"
                        sendEvent(name: "so2_value", value: display_value as String, unit: "ppm", isStateChange: true)
                    } else
                    	sendEvent(name: "so2_value", value: "--", unit: "ppm", isStateChange: true)

                    if( resp.data.list[0].coValue != "-" ) {
                        log.debug "CO: ${resp.data.list[0].coValue}"
                        display_value = "\n" + resp.data.list[0].coValue + "\n"
                        sendEvent(name: "carbonMonoxide", value: resp.data.list[0].coValue, unit: "ppm", isStateChange: true)
                        sendEvent(name: "co_value", value: display_value as String, unit: "ppm", isStateChange: true)
                    } else
                    	sendEvent(name: "co_value", value: "--", unit: "ppm", isStateChange: true)

                    def khai_text = "알수없음"
                    if( resp.data.list[0].khaiValue != "-" ) {
                        def khai = resp.data.list[0].khaiValue as Integer
                        log.debug "Khai value: ${khai}"

                        def station_display_name = resp.data.parm.stationName

                        if (fakeStationName)
                        	station_display_name = fakeStationName
                       
	                    sendEvent(name:"data_time", value: " " + station_display_name + " 대기질 수치: ${khai}\n 측정 시간: " + resp.data.list[0].dataTime, isStateChange: true)
                        
                        
                        uploadDataToThingSpeak(khai)
                  		sendEvent(name: "airQuality", value: khai, isStateChange: true)

                        if (khai > 250 ) khai_text="매우나쁨"
                        else if (khai > 100) khai_text="나쁨"
                        else if (khai > 50) khai_text="보통"
                        else if (khai >= 0) khai_text="좋음"

                        sendEvent(name: "airQualityStatus", value: khai_text, unit: "", isStateChange: true)
                        sendEvent(name: "maskIcon", value: khai_text, unit: "", isStateChange: true)

                    } else {
                        def station_display_name = resp.data.parm.stationName

                        if (fakeStationName)
                        	station_display_name = fakeStationName


	                    sendEvent(name:"data_time", value: " " + station_display_name + " 대기질 수치: 정보없음\n 측정 시간: " + resp.data.list[0].dataTime, isStateChange: true)
                    	sendEvent(name: "airQualityStatus", value: khai_text, isStateChange: true)
                    }
          		}
            	else if (resp.status==429) log.debug "You have exceeded the maximum number of refreshes today"
                else if (resp.status==500) log.debug "Internal server error"
            }
        } catch (e) {
            log.error "error: $e"
        }
	}
    
    else log.debug "Missing data from the device settings station name or access key"
}

// WunderGround weather handle commands
def pollWunderground() {
	log.debug "pollAirKorea()"
    log.debug "Data will repoll every ${60} minutes"

	// Current conditions
	def obs = get()
	if (obs) {
		//def weatherIcon = obs.icon_url.split("/")[-1].split("\\.")[0]

		send(name: "temperature", value: Math.round(obs.temperature), unit: "C")
        def feelText = "알수없음"
        def feelTemp = Math.round(obs.temperatureFeelsLike as Double)
		send(name: "feelsLike", value: Math.round(obs.temperatureFeelsLike as Double), unit: "C") 
        
      	def clothText = "알수없음"
     	if(feelTemp <= 5.0) {
     		feelText = "매우추움" 
        	clothText = "패딩, 두꺼운코드, 목도리, 기모제품 등 두꺼운 겨울 옷 입기"
     	}
     	else if(feelTemp >= 6.0 && feelTemp <= 9.0) {
     		feelText = "추움" 
        	clothText = "코트, 히트텍, 니트, 가죽자켓 등 레이어드 필수"
     	}
        else if(feelTemp >= 10.0 && feelTemp <= 11.0) {
        	feelText = "약간추움" 
            clothText = "간절기 야상, 자켓, 트렌치코트 등 여러겹 껴입기"
        }
        else if(feelTemp >= 12.0 && feelTemp <= 16.0) {
        	feelText = "보통" 
            clothText = "자켓, 가디건, 야상, 맨투맨"
        }
        else if(feelTemp >= 17.0 && feelTemp <= 19.0) {
        	feelText = "약간더움"
            clothText = "니트, 가디건 걸치기"
        }
        else if(feelTemp >= 20.0 && feelTemp <= 22.0) {
        	feelText = "더움"
            clothText = "긴팔티, 면바지"
        }
        else if(feelTemp >= 23.0 && feelTemp <= 26.0) {
        	feelText = "매우더움" 
            clothText = "반팔, 얇은 셔츠, 반바지" 
        }
        else if(feelTemp >= 27.0) {
        	feelText = "매우매우더움"
            clothText = "민소매, 반팔, 반바지"
        }
        
		sendEvent(name: "feelsLikeIcon", value: feelText, unit: "", isStateChange: true)
        sendEvent(name: "clothes", value: "추천 옷차림: " + clothText, unit: "", isStateChange: true)
        
		send(name: "humidity", value: obs.relativeHumidity as Integer, unit: "%")
		send(name: "weather", value: obs.wxPhraseShort)
		send(name: "weatherIcon", value: obs.iconCode as String, displayed: false)
		//send(name: "wind", value: Math.round(obs.wind_mph) as String, unit: "MPH") // as String because of bug in determining state change of 0 numbers

		if (obs.local_tz_offset != device.currentValue("timeZoneOffset")) {
			//send(name: "timeZoneOffset", value: obs.local_tz_offset, isStateChange: true)
		}

   		def cityValue = "${loc.city}, ${loc.adminDistrictCode} ${loc.countryCode}"
		if (cityValue != device.currentValue("city")) {
			send(name: "city", value: cityValue)
		}

		send(name: "ultravioletIndex", value: Math.round(obs.uvIndex as Double))

		// Sunrise / Sunset
        def dtf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")

        def sunriseDate = dtf.parse(obs.sunriseTimeLocal)
        log.info "'${obs.sunriseTimeLocal}'"

        def sunsetDate = dtf.parse(obs.sunsetTimeLocal)

        def tf = new java.text.SimpleDateFormat("h:mm a")
        tf.setTimeZone(TimeZone.getTimeZone(loc.ianaTimeZone))

        def localSunrise = "${tf.format(sunriseDate)}"
        def localSunset = "${tf.format(sunsetDate)}"
        
        send(name: "localSunrise", value: localSunrise, descriptionText: "Sunrise today is at $localSunrise")
        send(name: "localSunset", value: localSunset, descriptionText: "Sunset today at is $localSunset")

        //send(name: "illuminance", value: estimateLux(obs, sunriseDate, sunsetDate))

		// Forecast
        def f = getTwcForecast(zipCode)
         if (f) {
            def icon = f.daypart[0].iconCode[0] ?: f.daypart[0].iconCode[1]
            def value = f.daypart[0].precipChance[0] as Integer ?: f.daypart[0].precipChance[1] as Integer
            def narrative = f.daypart[0].narrative
            send(name: "percentPrecip", value: value, unit: "%")
            //send(name: "forecastIcon", value: icon, displayed: false)
        }
		else {
			log.warn "Forecast not found"
		}
	}
	else {
		log.warn "No response from Weather Underground API"
	}
}

private get() {
	getTwcConditions(zipCode)
}

private localDate(timeZone) {
	def df = new java.text.SimpleDateFormat("yyyy-MM-dd")
	df.setTimeZone(TimeZone.getTimeZone(timeZone))
	df.format(new Date())
}

private send(map) {
	log.debug "WUSTATION: event: $map"
	sendEvent(map)
}

private uploadDataToThingSpeak(data) {
	log.debug "upload data: $data"
    
    def url = "http://api.thingspeak.com/update?key=80VZ99CPI11L3D5U&field1=${data}"
    httpGet(url) { 
        response -> 
        if (response.status != 200 ) {
            log.debug "ThingSpeak logging failed, status = ${response.status}"
        } else {
        	log.debug "ThingSpeak logging successful, status = ${response.status}"
        }
    }
    
}

private uploadDataToThingSpeak2(data) {
	log.debug "upload data: $data"
    
    def url = "http://api.thingspeak.com/update?key=6DNFNIBMGHZSHY2Y&field1=${data}"
    httpGet(url) { 
        response -> 
        if (response.status != 200 ) {
            log.debug "ThingSpeak logging failed, status = ${response.status}"
        } else {
        	log.debug "ThingSpeak logging successful, status = ${response.status}"
        }
    }

}

// handle commands
def take() {
	log.debug "Executing 'take'"
	// TODO: handle 'take' command
    def params = [
        uri: "http://203.252.195.182:9000/airPollutionImage"
    ]

    try {
        httpGet(params) { response ->
            // we expect a content type of "image/jpeg" from the third party in this case
            if (response.status == 200 && response.headers.'Content-Type'.contains('image/png')) {
                def imageBytes = response.data
                if (imageBytes) {
                    def name = getImageName()
                    try {
                        storeImage("test", imageBytes)
                    } catch (e) {
                        log.error "Error storing image ${name}: ${e}"
                    }

                }
            } else {
                log.error "Image response not successful or not a png response"
            }
        }
    } catch (err) {
        log.debug "Error making request: $err"
    }
    
}

def getImageName() {
    return java.util.UUID.randomUUID().toString().replaceAll('-','')
}

def getGraphHTML() {
	def html = """
<!DOCTYPE HTML>
<html>
<head>
    <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</head>
<body>
<div id="chartContainer" style="height: 100%; max-width: 100%; margin: 0px auto;"></div>

<script>
    var dataPoints1 = [];
    var dataPoints2 = [];
    var dataPoints3 = [];
    var dataPoints4 = [];
    var dataPoints5 = [];
    var dataPoints6 = [];
    var dataPoints7 = [];

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            var obj = JSON.parse(this.responseText);
            for(i = 0; i < obj.feeds.length; i++){
                dataPoints1.push({label: new Date(obj.feeds[i].created_at), y: parseFloat(obj.feeds[i].field1)});
                dataPoints2.push({label: new Date(obj.feeds[i].created_at), y: parseFloat(obj.feeds[i].field2)});
                dataPoints3.push({label: new Date(obj.feeds[i].created_at), y: parseFloat(obj.feeds[i].field3)});
                dataPoints4.push({label: new Date(obj.feeds[i].created_at), y: parseFloat(obj.feeds[i].field4)});
                dataPoints5.push({label: new Date(obj.feeds[i].created_at), y: parseFloat(obj.feeds[i].field5)});
                dataPoints6.push({label: new Date(obj.feeds[i].created_at), y: parseFloat(obj.feeds[i].field6)});
                dataPoints7.push({label: new Date(obj.feeds[i].created_at), y: parseFloat(obj.feeds[i].field7)});
            }
        }

        var chart = new CanvasJS.Chart("chartContainer", {
            animationEnabled: true,
            exportEnabled: true,
            title:{
                text: "Air Pollution"
            },
            axisY:{
                title: "value"
            },
            toolTip: {
                shared: true
            },
            legend:{
                cursor:"pointer",
                itemclick: toggleDataSeries
            },
            data: [{
                type: "spline",
                name: "CAI",
                showInLegend: true,
                dataPoints: dataPoints1
            },
                {
                    type: "spline",
                    name: "pm10",
                    showInLegend: true,
                    dataPoints: dataPoints2
                },
                {
                    type: "spline",
                    name: "pm2.5",
                    showInLegend: true,
                    dataPoints: dataPoints3
                },
                {
                    type: "spline",
                    name: "O3",
                    showInLegend: true,
                    dataPoints: dataPoints4
                },
                {
                    type: "spline",
                    name: "NO2",
                    showInLegend: true,
                    dataPoints: dataPoints5
                },
                {
                    type: "spline",
                    name: "CO",
                    showInLegend: true,
                    dataPoints: dataPoints6
                },
                {
                    type: "spline",
                    name: "SO2",
                    showInLegend: true,
                    dataPoints: dataPoints7

                }]
        });

        chart.render();

        function toggleDataSeries(e) {
            if(typeof(e.dataSeries.visible) === "undefined" || e.dataSeries.visible) {
                e.dataSeries.visible = false;
            }
            else {
                e.dataSeries.visible = true;
            }
            chart.render();
        }

    }
    xhttp.open("GET", "https://api.thingspeak.com/channels/599878/feeds.json?results=60", true);
    xhttp.send();
</script>
</body>
</html>

"""
render contentType: "text/html", data: html, status: 200
}