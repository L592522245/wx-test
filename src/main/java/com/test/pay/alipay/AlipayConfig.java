package com.test.pay.alipay;

public class AlipayConfig {
	/*
	 * 以下参数为沙箱测试配置填写的参数，正式上线需更改响应参数
	 */
	// 商户appid
	public static String APPID = "2016082600313601";
	// 私钥 pkcs8格式的
	public static String RSA_PRIVATE_KEY = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQCszFOkoD3bSqOY/Kt3QEB13wvKoz9y8YczXUpn/Ss42I2UenKq8nW+S5X/xJ2iQErAt0kXzz5dDyMExM4COqG4W9ipRTPiMVINUuFaCceaHj7g3UIx3ZFXJngAeUHuVfQqFJDH6qOPYx2r6Z079IrR4Rg7m9327CaCINeTbS04K6A1RJu03MRy3msVLURbJoy9CO1tXLYG4so2P+MmmvDCmTZk+VC15Ku5BlkdpmnMM8qmn6J5QDaX6oU+i0b2Pbi6FgQKfWP3e4H0xodwbeiJYbIXvQCX+nW8wxLiH5rZifsw+HzlFP+PR/EvSIqDi786RwV0Z56fo7g/9SltAIiNAgMBAAECggEBAIFLynJubJp/uPYqaeqrKxCHuvz/NeS+ERo//gLdjs/USewFy3NaRPwE9tIQvpq/XK/i9VBZhCKBxwQ0dql4UbXvE6lk3LOtj+XkVDbZBn+tjgMXzAYf8jax1C0WjlzV+ALcrJDKY2RRF8yg20QXcSAmO8mukWHHxfCsvmHT2Xgi+8JYDm0jBv9PLeUrq4NE2n/etJoxlr6udYZOjbGgXWcGbQzzFP4Xxi5FQUjWisqhUxXSpgRDJ6gEYkahA81JrR7Ie7BNI306qwsk5aZb1S/ZZuPgFSzUY3ooLaGuBHggi6OIQJsh4MWx5A18zV+DehOTY70Smge9i04RLeq0D8kCgYEA/+tdcz2nUAZ99PtfBQWgO24wb+SOtve3HwwfCzcAl7dLtvHQmXBNgM7PAhBGULWWNmDUwz2wmyebHqhEdx5NDe3DIMLOa6IFQAq3gjBnVX4Eo/wF92rDV9yQ5OQ1pz+v6bHhY6EuCa1Pi3ZKPHNgq5P3qIJauJKSBMIZH/1FRTcCgYEArNpCcvnbwacdxOV1FCVL3D20tP3d6eztu3LGilFG7iSkxdXIbi31codYDbxi6JWWJZKIyuDtkKsE30dA1mNsLkHD0F4TKW91e8H8o/Ho4gVYsoj5RxtvzyC2R1/XOvQGQWC/5U3e5o7GHYmhCt/alxLwkul6BV81tM8o/ycdglsCgYEAsAceciPYL40nmhhE1Vl3SymRm4UkPOnKRUq7F6WvfKuRIrbVepTqWkzlyk+TbfRY6/JSpjCKh04Ivl+TKDPpYElITAJypUn+PnygmKXejcluO7UgDXyb/JUz/JyT4wRFZYL6uuKknfddnVmx2j6qIZpXYMq4x/fZA/jV+1DkEqUCgYEAoTVF9/lzrR5xMytVkB5gq92Y5ZxHFV51MfePl3zKDFx8mE4UCj4uxkvYIadrD8RDLnOFxMEWj3AQsTTBEbHUOSf4uiKi+GgNSAjZ2QVKz1FxMcFxy5GZK7I0jkiicUcZdmwHTJLdqmXJ+85aNcwlD/UIDdxWgyAS9LioYZ9XdDUCgYEApxB4wSEOVX2YMt6HwBbHMLK94xgOt9Rjx9J2fvfVpoqDCXe3hN0GHe5e4wntjZe3h4omDPlyRsGQMI096vPr762nxpzJZIXZECxn/GrMphbFQj9KcW6EjZsu0s2x1gz9F+aOuJWkBow+FjWqZUn6U5UTWzPwsURBQjyTJbAmDU4=";
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://l1867227l4.iask.in/pay/notify_url";
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://l1867227l4.iask.in/pay/return_url";
	// 请求网关地址 正式地址：https://openapi.alipay.com/gateway.do
	public static String URL = "https://openapi.alipaydev.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm4oeNy5mKRPbUisK/aKPyh4LjpUkI2b9kizy2vzb1W8qTW9KVlQc06wk4z9FlxFC0wCVuNdQj8DhGPeTxOCkTjdkw7y8kbYwxvq/ISVOxb8Qum+/6X+I5N684vcUW/+T4mg16yjvWVCC34Uv3u2hiweQph+rsNfB4U7Ttg/BtoKf3V/PjLGIhu1GSgJYwDqX8FWCSPGJ+VFLyoyeNoCJbxybvcEzJx3lhQoFGbcV3BcOwu4foqjc2kzH/iZNEuyIyRIR3HFo7wI0v/ANgA6cIb5kHcBnhLW+Cv065u7yoyd+Bd3PunUq5uOTcjTSRgi8Bl4+525DFai7RXJ6WVBFrQIDAQAB";
	// 日志记录目录
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";
}
