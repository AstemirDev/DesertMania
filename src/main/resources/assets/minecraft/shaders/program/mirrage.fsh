#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DiffuseDepthSampler;

uniform float GlobalTime;
uniform int DayTime;
uniform int HotArea;

uniform vec3 ScreenSize;

in vec2 texCoord;
in vec2 oneTexel;


out vec4 fragColor;

uniform float max_shift = 1.2;
uniform float speed = 0.002;
uniform float min_heathaze_dist = 0.995;



float random( vec2 pos )
{ 
    return fract(sin(dot(pos, vec2(12.9898,78.233))) * 43758.5453);
}

float noise( vec2 pos )
{
    return random( floor( pos ) );
}

float value_noise( vec2 pos )
{
    vec2 p = floor( pos );
    vec2 f = fract( pos );

    float v00 = noise( p + vec2( 0.0, 0.0 ) );
    float v10 = noise( p + vec2( 1.0, 0.0 ) );
    float v01 = noise( p + vec2( 0.0, 1.0 ) );
    float v11 = noise( p + vec2( 1.0, 1.0 ) );

    vec2 u = f * f * ( 3.0 - 2.0 * f );

    return mix( mix( v00, v10, u.x ), mix( v01, v11, u.x ), u.y );
}

void main() {
    int roundedTime = DayTime % 24000;
    if (HotArea == 1 && (roundedTime > 22000 || roundedTime < 13000)){
        float time = GlobalTime;
        float depth = max(texture(DiffuseDepthSampler, texCoord).r - min_heathaze_dist, 0.0);
        vec2 uv_r = texCoord + vec2(time * speed, 0.0);
        vec2 uv_g = texCoord + vec2(-10.0, -time * speed);
        vec2 shift = vec2(
        sin(
        value_noise(uv_r * 8.0) * 0.2
        +   value_noise(uv_r * 16.0) * 0.2
        +   value_noise(uv_r * 32.0) * 0.2
        +   value_noise(uv_r * 64.0) * 0.2
        +   value_noise(uv_r * 128.0) * 0.2
        ) - 0.5
        , sin(
        value_noise(uv_g * 8.0) * 0.2
        +   value_noise(uv_g * 16.0) * 0.2
        +   value_noise(uv_g * 32.0) * 0.2
        +   value_noise(uv_g * 64.0) * 0.2
        +   value_noise(uv_g * 128.0) * 0.2
        ) - 0.5
        ) * (depth * max_shift);
        float depth_shifted = texture(DiffuseDepthSampler, texCoord + shift).r - min_heathaze_dist;

        if (depth_shifted > 0.0){
            fragColor = vec4(texture(DiffuseSampler, texCoord + shift).rgb, float(0.0 < depth_shifted));
        } else {
            fragColor = vec4(texture(DiffuseSampler, texCoord).rgb, 1.0);
        }
    }else {
        fragColor = vec4(texture(DiffuseSampler, texCoord).rgb, 1.0);
    }
}