from flask import Flask, request, jsonify
import logging
from vertex_lib import *

app = Flask(__name__)
logging.basicConfig(level=logging.INFO)


@app.route('/generate-question', methods=['GET'])
def generate_question():
    topic = request.args.get('topic')

    if not topic:
        return jsonify({"error": "Topic parameter is required"}), 400

    model_arch = ChatMainLLM.VERTEXLLM.value
    model_name = ChatMainLLMName.VERTEX_GEMINI_15_PRO_2

    instruction_prompt = f"Generate one university-level open-ended question about {topic}."


    chat_history = [
    Message(role="user", message=instruction_prompt, message_content_type=ChatInputContentType.TEXT, message_type= "TEXT", message_uri=""),
    Message(role="assistant", message="It is generating----", message_content_type=ChatInputContentType.TEXT, message_type="TEXT", message_uri="")
        ]

    timeout = 60

    logger = logging.getLogger(__name__)
    logger.setLevel(logging.INFO)

    logger.info(f"Generated instruction prompt: {instruction_prompt}")


    curl_vertex = CurlVertex(logger = logger, llm_config=LLMConfig({"model_arch": model_arch, "model_name": model_name, "temperature": 0.5, "top_k": 10, "top_p": 0.95}))

    response_content = ""
    for response in curl_vertex.generate(
            instruction_prompt=instruction_prompt,
            chat_history=chat_history,
            is_streaming=False,
            return_tokens=False,
            timeout=60
    ):
        response_content += response.content

    return jsonify({
        "question": response_content.strip()
    })

if __name__ == '__main__':
    app.run(debug=True, port= 8080)

